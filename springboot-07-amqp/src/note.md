# Spring消息

## 一、RabbitMQ

AMQP（Advanced Message Queue Protocol）的开源实现

### 1. 核心概念

#### 1) Message

消息 由 消息头，消息体组成。

是publisher给代理发送的内容。

消息头是一系列可选属性组成，包括route-key(指定消息发给谁)、priority(相对于其他消息的优先权)、delivery-mode(指出该消息可能需要持久性存储)



#### 2) Publisher

P 消息的生产者，是一个向交换器发布消息的客户端应用程序



#### 3) Exchange

交换器，接受P发来的消息，并将消息路由到相应队列

Exchange有4种类型：direct，fanout，topic，headers



#### 4) Queue

消息队列，保存消息，是消息的容器。等待消费者取走

一个消息可以投入一个或多个队列

#### 5) Binding

绑定，用于消息队列和交换器之间的关联。一个绑定就是基于路由键将交换器和消息队列连接起来的路由规则，所以可以将交换器理解成一个由绑定构成的路由表

Exchange和Queue的绑定可以是多对多的关系



#### 6) Connection

网络连接，比如一个TCP连接



#### 7) Channel

信道，多路复用连接中的一条独立的双向数据流通道。信道是建立在真实的TCP连接内的虚 拟连接， AMQP 命令都是通过信道发出去的，不管是发布消息、订阅队列还是接收消息，这 些动作都是通过信道完成。因为对于操作系统来说建立和销毁 TCP 都是非常昂贵的开销，所 以引入了信道的概念，以复用一条 TCP 连接

用于解决多路复用。建立一条TCP连接，在连接中开多个信道。



#### 8) Consumer

消息的消费者

#### 9) Virtual Host

虚拟主机vhost，在RabbitMQ中可以划分出很多的虚拟主机，每一个都是mini版的RabbitMQ服务器。包含交换机、信息队列和相关对象。虚拟主机之间相互隔离。连接RabbitMQ时要指定虚拟主机。默认vhost是/

#### 10) Broker

消息代理，就是服务器实体



P发送消息给消息队列服务器，先给服务器中的Exchange交换器，Exchange根据指定的route-key，派给相应队列。派发规则由Binding做绑定

P发消息给broker中的vhost，vhost中的exchange根据route-key查看相应Binding发给相应queue，然后consumer可以从queue中取出消息，先是建立connection，connection中有很多channel。



### 2. RabbitMQ运行机制

AMQP中的消息路由

AMQP相比JMS，增加了Exchange和Binding

Exchange四种direct, fanout, topic, ~~header~~

direct: route-key完全对应，匹配、单播，点对点

fanout: 分给所有绑定队列，最快，订阅模型

topic: 模糊匹配。将路由键和绑定键的字符串切分成单词，单词之间用点隔开。同样会识别两个通配符: 符号"#" "*" #匹配0个或多个单词，\*匹配一个单词









