package br.com.marcoaurelio.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data //criar os getters e setters automáticamente
@Entity(name = "tb_tasks")
public class TaskModel {

    @Id //identificar o "id" como chave primária
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private String description;


    @Column(length = 50) //limitar somente 50 caracteres
    private String title;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String priority;
    
    
    private UUID idUser;

    @CreationTimestamp
    private LocalDateTime createdAt;  
    
    public void setTitle(String title) throws Exception{
        if(title.length() > 50){
            throw new Exception("O campo title deve conter no máximo 50 caracteres");
        }
        this.title = title;
    }

}
