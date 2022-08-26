package br.com.curso.aws_project01.service;

import br.com.curso.aws_project01.enums.EventType;
import br.com.curso.aws_project01.model.Envelop;
import br.com.curso.aws_project01.model.Product;
import br.com.curso.aws_project01.model.ProductEvent;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.Topic;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ProductPublisherService {
    private static final Logger log = LoggerFactory.getLogger(ProductPublisherService.class);

    private AmazonSNS snsClient;
    private Topic productEventsToic;
    private ObjectMapper objectMapper;

    public ProductPublisherService(AmazonSNS snsClient,
                                   @Qualifier("productEventsTopic")Topic productEventsToic,
                                  ObjectMapper objectMapper){
        this.snsClient = snsClient;
        this.productEventsToic = productEventsToic;
        this.objectMapper = objectMapper;
    }

    public void publishProductEvent(Product product, EventType eventType, String username){

        ProductEvent productEvent = new ProductEvent();
        productEvent.setProductId(product.getId());
        productEvent.setCode(product.getCode());
        productEvent.setUsername(username);

        Envelop envelop = new Envelop();
        envelop.setEnventType(eventType);

        try {
            envelop.setData(objectMapper.writeValueAsString(productEvent));
           PublishResult publishResult = snsClient.publish(productEventsToic.getTopicArn(),
                                objectMapper.writeValueAsString(envelop));


            log.info("Product event send - Event: {} - ProductId: {} - MessageId: {}",
                    envelop.getEnventType(),
                    productEvent.getProductId(),
                    publishResult.getMessageId());

        } catch (JsonProcessingException e) {
            log.error("Failed to create product event message");
        }
    }
}
