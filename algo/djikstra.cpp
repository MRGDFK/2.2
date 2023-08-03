#include <bits/stdc++.h>

using namespace std;

typedef pair<int, int> pii;

void dijkstra( vector<vector<pii>>& graph, int source, vector<int>& distances,vector<int>&parents) {
    int n = graph.size();
    distances.assign(n, numeric_limits<int>::max());
    distances[source] = 0;
    parents.assign(n,-1);

    priority_queue<pii, vector<pii>, greater<pii>> pq;
    pq.push({0, source});
    while (!pq.empty()) {
        int u = pq.top().second;
        int d = pq.top().first;
        pq.pop();

        if (d > distances[u])
            continue;

        for (auto neighbor : graph[u]) {
            int v = neighbor.first;
            int w = neighbor.second;

            if (distances[u] + w < distances[v]) {
                distances[v] = distances[u] + w;
                parents[v]=u;
                pq.push({distances[v], v});
            }
        }
    }
}
void printPath(int vertex, const vector<int>& parents) {
    stack<int> path;
    while (vertex != -1) {
        path.push(vertex);
        vertex = parents[vertex];
    }

    cout << "Shortest path: ";
    while (!path.empty()) {
        cout << path.top() << " ";
        path.pop();
    }
    cout << endl;
}
int main() {
    int n, m;
    cin >> n >> m;

    vector<vector<pii>> graph(n);

    for (int i = 0; i < m; ++i) {
        int u, v, w;
        cin >> u >> v >> w;
        graph[u].push_back({v, w});
    }

    int source;
    cin >> source;

    vector<int> distances;
    vector<int>parents;
    dijkstra(graph, source, distances,parents);

    for (int i = 0; i < n; ++i) {
        cout << "Shortest distance from source to vertex " << i << ": ";
        if (distances[i] == numeric_limits<int>::max())
            cout << "Infinity   ";
        else
            cout << distances[i]<<"   ";

           printPath(i,parents);
        cout << endl;
    }

    return 0;
}

/*
5 7
0 1 2
0 2 4
1 2 1
1 3 7
2 3 3
2 4 6
3 4 5
0
*/
