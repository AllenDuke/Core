# 经典算法
## 图论
### 最短路径算法
#### 迪杰斯特拉算法 单源最短路径
![shortestPath-dijkstra1](../images/shortestPath-dijkstra1.PNG)
1. (v0,v1,1), (v0,v2,5), (v0,...,~)  ~意为无穷大
2. (v1,v2,1+3<5,更新到v2的路径)...
#### Floyd算法 多源最短路径（可求任意两点之间的最短路径）做题时建议使用此算法
从任意节点i到任意节点j的最短路径不外乎2种可能：
1) 直接从节点i到节点j。
2) 从节点i经过若干个节点k到节点j。
所以，我们假设arcs(i,j)为节点i到节点j的最短路径的距离，对于每一个节点k，我们检查arcs(i,k) + arcs(k,j) < arcs(i,j)是否成立，
如果成立，证明从节点i到节点k再到节点j的路径比节点i直接到节点j的路径短，我们便设置arcs(i,j) = arcs(i,k) + arcs(k,j)，这样一来，
当我们遍历完所有节点k，arcs(i,j)中记录的便是节点i到节点j的最短路径的距离。（由于动态规划算法在执行过程中，需要保存大量的临时状态
（即小问题的解），因此它天生适用于用矩阵来作为其数据结构，因此在本算法中，我们将不使用Guava-Graph结构（可用来做dijkstra算法），
而采用邻接矩阵来作为本例的数据结构）

![shortestPath-dijkstra1](../images/shortestPath-floyd.PNG)
1. 当不经过任意第三节点时，其最短路径为初始路径。
2. 枚举中转节点。
```java
for (int k = 1; k <= vexCount; k++) { //并入中转节点1,2,...vexCount，把k放到最外层？
    for (int i = 1; i <= vexCount; i++) {
        for (int j = 1; j < vexCount; j++) {
            if (arcs[i][k] + arcs[k][j] < arcs[i][j]) {
                arcs[i][j] = arcs[i][k] + arcs[k][j];
            }
        }
    }
} 
```
### 最小生成树
#### Kruskal算法 关注边
![smallTree-kruskal](../images/smallestTree-kruskal.PNG)
#### Prim算法 关注点
![smallTree-prim](../images/smallestTree-prim.PNG)
