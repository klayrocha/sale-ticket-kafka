package br.com.klayrocha.service;

import java.math.BigDecimal;
import java.util.Properties;
import java.util.Random;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import br.com.klayrocha.model.Sale;
import br.com.klayrocha.serializer.SaleSerializer;

public class SalesGenerator {

	private static Random rand = new Random();
	private static long operation = 0;
	private static BigDecimal ticketValue = BigDecimal.valueOf(500);

	public static void main(String[] args) throws InterruptedException {

		Properties properties = new Properties();
		properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, SaleSerializer.class.getName());
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, SaleSerializer.class.getName());

		try (KafkaProducer<String, Sale> producer = new KafkaProducer<String, Sale>(properties)) {
			while (true) {
				Sale sale = generateSale();
				ProducerRecord<String, Sale> record = new ProducerRecord<String, Sale>("sell-tickets", sale);
				producer.send(record);
				Thread.sleep(200);
			}
		}
	}

	private static Sale generateSale() {
		long client = rand.nextLong();
		int numberOfTickets = rand.nextInt(10);
		return new Sale(operation++, client, numberOfTickets,
				ticketValue.multiply(BigDecimal.valueOf(numberOfTickets)));
	}
}
