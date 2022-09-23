package com.example.domains.entities.dtos;

import lombok.Value;

@Value
public class KeyValueDto<K, V> {
	private K key;
	private V value;
}
