import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Quiz {
    @Id
    @GeneratedValue
    private Integer id;

    @NotNull

    @ManyToOne(fetch = FetchType.LAZY)
    private SubCategory subCategory;

    @NotNull
    @Column(unique = true)
    @Size(max = 128)
    private String question;

    @NotNull
    @Size(max = 128)
    private String answer_1;
    @NotNull
    @Size(max = 128)
    private String answer_2;
    @NotNull
    @Size(max = 128)
    private String answer_3;
    @NotNull
    @Size(max = 128)
    private String answer_4;

    @Range(min = 0, max = 3)
    private Integer correct;

    public String getQuestion() {
        return question;
    }

    public String getAnswer_1() {
        return answer_1;
    }

    public String getAnswer_2() {
        return answer_2;
    }

    public String getAnswer_3() {
        return answer_3;
    }

    public String getAnswer_4() {
        return answer_4;
    }

    public Integer getCorrect() {
        return correct;
    }

    public void setQuestion(String question) {
        this.question = question;
    }


    public void setAnswer_1(String answer_1) {
        this.answer_1 = answer_1;
    }


    public void setAnswer_2(String answer_2) {
        this.answer_2 = answer_2;
    }


    public void setAnswer_3(String answer_3) {
        this.answer_3 = answer_3;
    }


    public void setAnswer_4(String answer_4) {
        this.answer_4 = answer_4;
    }


    public void setCorrect(Integer correct) {
        this.correct = correct;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public Integer getId() {
        return id;
    }
}
