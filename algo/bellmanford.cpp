#include <bits/stdc++.h>
using namespace std;

struct Edge {
    int u, v, w;
};

vector<Edge> E;
int dist[1000];
int n;

void BellmanFord(int s) {
    for (int i = 1; i <= n; i++) {
        dist[i] = 100000000;
    }
    dist[s] = 0;

    for (int i = 1; i < n ; i++) {
        for (Edge e : E) {
            if (dist[e.v] > dist[e.u] + e.w) {
                dist[e.v] = dist[e.u] + e.w;
            }
        }
    }
}

int main() {
    int node, e;
    cout << "Enter the number of vertices: ";
    cin >> node;
    n = node;

    cout << "Enter the number of edges: ";
    cin >> e;

    cout << "Enter the edges (source, destination, weight):\n";
    while(e--) {
        Edge edge;
        cin >> edge.u >> edge.v >> edge.w;
        E.push_back(edge);
    }

    int s;
    cout << "Enter the source vertex: ";
    cin >> s;

    BellmanFord(s);

    cout << "Shortest distances from source " << s << ":\n";
    for (int i = 1; i <= node; i++) {
        cout << "Vertex " << i << ": " << dist[i] << "\n";
    }

    return 0;
}
