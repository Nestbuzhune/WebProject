package com.imagecolectionsystem1.nest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imagecolectionsystem1.nest.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
