package 每周20道力扣.第一周;

import java.util.*;

public class MicroservicesCohesion {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] edges = new int[n];

        for (int i = 0; i < n; i++) {
            edges[i] = scanner.nextInt();
        }

        List<Integer> result = findMaxCohesionGroup(edges);
        for (int num : result) {
            System.out.print(num + " ");
        }
        scanner.close();
    }

    private static class ServiceGroup implements Comparable<ServiceGroup> {
        int startNode;
        List<Integer> members;
        int reach;

        public ServiceGroup(int startNode, List<Integer> members, int reach) {
            this.startNode = startNode;
            this.members = members;
            this.reach = reach;
        }

        @Override
        public int compareTo(ServiceGroup other) {
            int h1 = this.members.size() - this.reach;
            int h2 = other.members.size() - other.reach;
            if (h1 != h2) {
                return Integer.compare(h2, h1);
            } else {
                return Integer.compare(Collections.max(other.members),
                        Collections.max(this.members));
            }
        }
    }

    public static List<Integer> findMaxCohesionGroup(int[] edges) {
        int n = edges.length;
        boolean[] visited = new boolean[n];
        boolean[] inStack = new boolean[n];
        List<ServiceGroup> groups = new ArrayList<>();
        Map<Integer, Set<Integer>> reachMap = new HashMap<>();

        // Step 1: Find all cycles using DFS
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs(i, edges, visited, inStack, new ArrayList<>(), groups, reachMap);
            }
        }

        // Sort the groups based on cohesion and then by maximum node in descending order
        Collections.sort(groups);

        // Return the group with the highest cohesion
        ServiceGroup best = groups.get(0);
        return best.members;
    }

    private static void dfs(int node, int[] edges, boolean[] visited, boolean[] inStack,
                            List<Integer> path, List<ServiceGroup>
                                    groups, Map<Integer, Set<Integer>> reachMap) {
        if (inStack[node]) {
            // Found a cycle
            int index = path.indexOf(node);
            List<Integer> cycle = new ArrayList<>(path.subList(index, path.size()));
            int minNode = Collections.min(cycle);
            Set<Integer> reachSet = new HashSet<>();
            for (int member : cycle) {
                reachSet.addAll(reachMap.getOrDefault(member, new HashSet<>()));
            }
            groups.add(new ServiceGroup(minNode, cycle, reachSet.size()));
            return;
        }

        if (visited[node]) {
            return;
        }

        visited[node] = true;
        inStack[node] = true;
        path.add(node);

        int next = edges[node];
        reachMap.computeIfAbsent(next, k -> new HashSet<>()).add(node);
        dfs(next, edges, visited, inStack, path, groups, reachMap);

        path.remove(path.size() - 1);
        inStack[node] = false;
    }

}

