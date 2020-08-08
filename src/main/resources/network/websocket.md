# websocket
全双工，通过http升级为websocket，可实现服务端推送。
http请求
```java
GET /chat HTTP/1.1
Host: server.example.com
Upgrade: websocket //升级后的协议
Connection: Upgrade //表明要升级
Sec-WebSocket-Key: x3JJHMbDL1EzLkh9GBhXDw== //密钥
Sec-WebSocket-Protocol: chat, superchat
Sec-WebSocket-Version: 13
Origin: http://example.com
```
http响应
```java
HTTP/1.1 101 Switching Protocols
Upgrade: websocket
Connection: Upgrade
Sec-WebSocket-Accept: HSmrc0sMlYUkAGmm5OPpG2HaGWk=
Sec-WebSocket-Protocol: chat
```
## 关于多人在线贪吃蛇游戏的设计方案
当开始一局游戏时，服务端保存一份画面的数据，总体上，当服务端画面发生变动时，数据主动推送、同步到各玩家，各玩家接收、刷新画面。

实际上，每一帧的画面都在变动，但我们还是可以减少数据的传输的，例如在正常情况下，在玩家新收到画面时，如果此后玩家不操作，
那么玩家画面与服务端画面是同步的，即此时虽然玩家与服务端不进行数据交互，但画面是一致的。
画面变动情况：
1. 玩家进行了移动操作。那么该玩家自己不刷新画面，而是上传单个操作数据，服务端收到后，向**所有玩家**推送最新数据。
2. 新进入玩家。
3. 某玩家死亡或掉线。

服务端应保持自己与各玩家的画面一致，这也就要求网络低延迟，传输的数据量应尽量少，更新时速度应尽量快。
