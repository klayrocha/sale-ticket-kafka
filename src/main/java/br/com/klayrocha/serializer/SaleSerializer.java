package br.com.klayrocha.serializer;

import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.klayrocha.model.Sale;

public class SaleSerializer implements Serializer<Sale> {

	@Override
	public byte[] serialize(String topic, Sale sale) {
		try {
			return new ObjectMapper().writeValueAsBytes(sale);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
