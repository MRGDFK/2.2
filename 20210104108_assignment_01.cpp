#include <bits/stdc++.h>
using namespace std;
typedef pair<int,int>pr;
vector<pr> graph[1002];
int vis[1003];
vector<int> res;
pair<int,int>cst[100];
int mx=1999;
void relax(int u, int v, int w)
{
    if (cst[v].second > cst[u].second + w)
    {
        cst[v].second = cst[u].second + w;
        cst[v].first = u;
    }
}
void DFS(int node)
{
    vis[node] = 1;
    // cout<<node<<" -> ";

    for (auto child: graph[node])
    {
        if (vis[child.first] == 0)
        {
            DFS(child.first);
        }
    }
    res.push_back(node);
}
void DAG_Shortest_Path(int node,int n)
{
    for (int i =1; i<= n; i++)
    {
        if (vis[i]==0)
        {
            DFS(i);
        }
    }

    for (int i=1; i<=n;i++)
    {
        if (i==node)
        {
            cst[i].first = 0;
            cst[i].second=0;
        }
        else{

            cst[i].first = 0;
            cst[i].second=mx;
        }
    }

    for(int j=1; j<=n; j++)
    {
        for(auto v:graph[j])
        {
            relax(j, v.first, v.second);
        }
    }

}
void path_print(int v)
{
    if(cst[v].first==0){
        cout<<v<<" ";
        return;
    }
    path_print(cst[v].first);
    cout<<v<<" ";

}
int main()
{
    int n, m;
    cin >> n >> m;
    while (m--)
    {
        int x, y, z;
        cin >> x >> y >> z;
        graph[x].push_back(make_pair(y, z));
    }
    int src;
    cout << "Enter Source" << endl;
    cin >> src;
    DAG_Shortest_Path(src, n);
    for (int i = 1; i <= n; i++)
    {

        cout << "Path " << i << " "
            << "Cost : " << cst[i].second << endl;
    }
    for (int i = 1; i <= n; i++)
    {

        cout << "Path : " << endl;
        path_print(i);
        cout << "cost: " << cst[i].second << endl;
    }
    return 0;
}
