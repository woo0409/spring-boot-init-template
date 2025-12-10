package top.sharehome.springbootinittemplate.model.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private Long id;

    private String comment;

    private Integer score;
}
