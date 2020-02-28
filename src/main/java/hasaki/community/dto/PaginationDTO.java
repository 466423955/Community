package hasaki.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Create by hanzp on 2020-02-28
 */
@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;

    public void setPagination(Integer totalCount, Integer page, Integer size) {
        if(totalCount%size ==0){
            totalPage = totalCount/size;
        }else{
            totalPage = totalCount/size + 1;
        }

        page = page < 1 ? 1 : page;
        page = page > totalPage ? totalPage : page;
        this.page = page;

        //是否展示前一页
        if(page == 1){
            this.showPrevious = false;
        } else {
            this.showPrevious = true;
        }
        //是否展示后一页
        if(page == totalPage){
            this.showNext = false;
        } else{
            this.showNext = true;
        }

        //页码集合
        this.pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if(page-i < 1){
                break;
            }
            this.pages.add(page-i);
        }
        for (int i = 1; i <= 3; i++) {
            if(page+i > totalPage){
                break;
            }
            this.pages.add(page+i);
        }
        Collections.sort(this.pages);

        //是否展示首页
        if(this.pages.contains(1)){
            this.showFirstPage = false;
        } else{
            this.showFirstPage = true;
        }

        //是否展示末页
        if(this.pages.contains(totalPage)){
            this.showEndPage = false;
        } else{
            this.showEndPage = true;
        }
    }
}
