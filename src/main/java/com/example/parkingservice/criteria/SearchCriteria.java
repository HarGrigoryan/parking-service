package com.example.parkingservice.criteria;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;

@Getter
@Setter
public class SearchCriteria {

    private static final int DEFAULT_PAGE_SIZE = 20;

    private int pageNumber;
    private int pageSize;


    public PageRequest buildPageRequest() {
        int pageNumber = Math.max(this.pageNumber, 0);
        int pageSize = this.pageSize <= 0 ? DEFAULT_PAGE_SIZE : this.pageSize;

        return PageRequest.of(pageNumber, pageSize);
    }

}