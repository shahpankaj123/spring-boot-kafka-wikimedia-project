package com.consumer.kafka_consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.consumer.kafka_consumer.entity.WikimediaData;

public interface WikimediaDataRepository extends JpaRepository<WikimediaData, Long> {

}
