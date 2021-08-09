package com.greenfoxacademy.ticketingsystem.models;

import com.sun.istack.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="activity")
@Builder(toBuilder = true)
@SuppressWarnings("JpaDataSourceORMInspection")
@AllArgsConstructor
public class Activity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false,updatable = false)
  private Long Id;
  @Column(nullable = false)
  private String code;
  @Column(nullable = false)
  @NotNull
  private String modification;
  @Column(nullable = false)
  private LocalDateTime upDatedAt;
  @Column(nullable = false)
  private String modifiedBy;

  @PrePersist
  void createdAt() {
    upDatedAt = LocalDateTime.now();
  }


}
