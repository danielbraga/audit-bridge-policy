package org.acme.audit;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.component.netty.http.NettyHttpMessage;

public class AuditBridgeProcess implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        Message message = exchange.getMessage();
        if (message != null) {
            System.out.println(message.toString());
        }

        NettyHttpMessage nettyHttpMessage = exchange.getIn(NettyHttpMessage.class);
        if (nettyHttpMessage != null) {
            io.netty.handler.codec.http.FullHttpRequest request = nettyHttpMessage.getHttpRequest();
            io.netty.handler.codec.http.FullHttpResponse response = nettyHttpMessage.getHttpResponse();
            if (request != null) {
                System.out.println(request.toString());
                // Read content without advancing the readerIndex, or slice it
                io.netty.buffer.ByteBuf content = request.content();
                if (content != null) {
                    // Convert ByteBuf to String safely without destroying it
                    String bodyString = content.toString(io.netty.util.CharsetUtil.UTF_8);
                    System.out.println("Content: " + bodyString);
                }
            }
            if (response != null) {
                System.out.println(response.toString());
            }
        }
    }
}
