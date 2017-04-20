package com.supermy.designpatterns.command;

/**
 * Created by moyong on 17/2/22.
 * 解耦了发送者和接受者之间联系。 发送者调用一个操作，接受者接受请求执行相应的动作，因为使用Command模式解耦，发送者无需知道接受者任何接口。
 * 调用者基本只和接口打交道,不合具体实现交互,这也体现了一个原则,面向接口编程,这样,以后增加第四个具体命令时,就不必修改调用者TestCommand中的代码了
 * 能实现Undo功能.每个具体命令都可以记住它刚刚执行的动作,并且在需要时恢复.
 */
public interface Command {
    public abstract void execute ( );
}
