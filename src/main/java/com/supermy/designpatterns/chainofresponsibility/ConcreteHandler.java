package com.supermy.designpatterns.chainofresponsibility;

/**
 * Created by moyong on 17/2/22.
 *
 * 因为无法预知来自外界（客户端）的请求是属于哪种类型，每个类如果碰到它不能处理的请求只要放弃就可以
 *
 * 与Command模式区别：

 Command 模式需要事先协商客户端和服务器端的调用关系，比如 1 代表 start 2 代表 move 等，这些 都是封装在 request 中，到达服务器端再分解。


 CoR 模式就无需这种事先约定，服务器端可以使用 CoR 模式进行客户端请求的猜测，一个个猜测 试验。

 */
public class ConcreteHandler implements Handler {
    private Handler successor;

    public ConcreteHandler(Handler successor) {
        this.successor = successor;
    }

    public void handleRequest(Request request) {
        if (request instanceof HelpRequest) {
            //这里是处理Help的具体代码
        } else if (request instanceof PrintRequest) {
            request.execute();
        } else
            //传递到下一个
            successor.handleRequest(request);

    }
}

