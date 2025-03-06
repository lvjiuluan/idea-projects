--CREATE TABLE tmp_export.dm_col_df_m1_mtd_performance_report_qy AS

SELECT 
    fm.month_tag AS assign_month,
    fm.month_day,
    fm.case_type,
    nv1(sum(fm.principal_fm),0) AS assign_principal,
    nv1(sum(fm.amount_fm),0) AS assign_amount,
    nv1(count(DISTINCT fm.case_id),0) AS assign_case_num,
    nv1(sum(if(fm.principal_fm < fm.repay_principal, fm.principal_fm, fm.repay_principal)),0) AS repay_principal,
    nv1(sum(if(fm.amount_fm < fm.repay_amount, fm.amount_fm, fm.repay_amount)),0) AS repay_amount,
    nv1(count(DISTINCT CASE WHEN fm.repay_principal >= fm.principal_fm THEN fm.case_id ELSE NULL END),0) AS repay_case_num
FROM (
    SELECT 
        substr(assign_date,1,7) AS month_tag,
        assign_date AS date_tag,
        count(1) AS assign_cnt
    FROM dwd.dwd_col_di_case_assignment
    WHERE dt >= '20240101' AND dt <= date_format(date_sub(current_date(),1),'yyyyMMdd')
    GROUP BY substr(assign_date,1,7), assign_date
) as t1
LEFT JOIN (
    SELECT 
        substr(assign_date,1,7) AS assign_month,
        assign_date,
        case_id,
        '新案' AS case_type,
        remain_principal,
        remain_amount
    FROM dwd.dwd_col_di_case_assignment
    WHERE user_opt = '0'
    AND collector_id = '320'
    AND overdue_days = '1'
    AND assign_date >= '2024-01-01'
    AND assign_date <= date_sub(current_date(),1)
) as t2 
ON t1.month_tag = t2.assign_month AND t1.assign_date <= t2.assign_date
LEFT JOIN (
    SELECT 
        substr(date_add(concat(substr(c.dt,1,4), '-', substr(c.dt,5,2), '-', substr(c.dt,7,2)),1),7) AS assign_month,
        substr(date_add(concat(substr(c.dt,1,4), '-', substr(c.dt,5,2), '-', substr(c.dt,7,2)),1),1,10) AS assign_date,
        case_id,
        '老案' AS case_type,
        sum(c.principal) - sum(c.repaid_amount) AS remain_principal,
        sum(c.amount) - sum(c.repaid_amount) AS remain_amount
    FROM dwd.dwd_col_di_case_info c
    WHERE c.business_id = '1'
    AND c.status = 'P'
    AND c.dt IN ('20231231', '20240131', '20240229', '20240331', '20240430', '20240531', '20240630', '20240731', '20240831', '20240930')
    AND c.overdue_days BETWEEN 2 AND 30
    GROUP BY substr(date_add(concat(substr(c.dt,1,4), '-', substr(c.dt,5,2), '-', substr(c.dt,7,2)),1),7),
             substr(date_add(concat(substr(c.dt,1,4), '-', substr(c.dt,5,2), '-', substr(c.dt,7,2)),1),1,10),
             case_id
) as t3 
ON t1.month_tag = t3.assign_month 
AND t3.assign_date <= t1.date_tag

LEFT JOIN (
    SELECT 
        nvl(t1.month_tag, t2.month_tag) AS month_tag,
        nvl(t1.month_day, t2.month_day) AS month_day,
        nvl(t1.case_id, t2.case_id) AS case_id,
        nvl(t1.case_type, '老案') AS case_type,
        nvl(t1.remain_principal, 0) AS remain_principal,
        nvl(t1.remain_amount, 0) AS remain_amount,
        nvl(t2.remain_principal, 0) AS remain_principal_fenbucha,
        nvl(t2.remain_amount, 0) AS remain_amount_fenbucha,
        nvl(t1.remain_principal, 0) + nvl(t2.remain_principal, 0) AS principal_fm,
        nvl(t1.remain_amount, 0) + nvl(t2.remain_amount, 0) AS amount_fm,
        nvl(t1.remain_principal, 0) + nvl(t2.remain_principal_tichu, 0) AS principal_fm_tichu,
        nvl(t1.remain_amount, 0) + nvl(t2.remain_amount_tichu, 0) AS amount_fm_tichu
    FROM t1 as t
    LEFT JOIN t2 as tt 
        ON t1.month_tag = tt.month_tag 
        AND t1.month_day = tt.month_day 
        AND t1.case_id = tt.case_id
) fm
ON t1.month_tag = fm.month_tag
AND t1.date_tag = fm.month_day
AND t1.case_id = fm.case_id
GROUP BY fm.month_tag, fm.month_day, fm.case_type;

