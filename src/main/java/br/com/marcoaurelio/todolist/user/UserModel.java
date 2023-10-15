package br.com.marcoaurelio.todolist.user;

import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_users") //nome da tabela que esses dados pertencem
//Como o é usado o conceito de ORM, precisamos falar qual tabela essa classe está se referenciando
public class UserModel {
    @Id // informar o banco de dados que esse id é chave primária
    @GeneratedValue(generator = "UUID") //gerar um ID de forma automática
    private UUID id;

    @Column(unique = true) //Valor único, não pode haver nomes iguais
    private String userName;
    private String name;
    private String password;

    @CreationTimestamp // quando o dado for salvo alguma informação, vai ser salvo a informação de qunado foi criado
    private LocalDate createdAt; 
}
