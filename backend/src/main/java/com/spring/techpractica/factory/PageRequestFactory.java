package com.spring.techpractica.factory;

import com.spring.techpractica.exception.ResourcesNotFoundException;
import org.springframework.data.domain.PageRequest;


public class PageRequestFactory {

    private PageRequestFactory() {

    }

    public static PageRequest createPageRequest(int pageSize, int pageNumber) {
        if (pageSize < 1 || pageNumber < 0) {
            throw new ResourcesNotFoundException("Page number or Size is negative");
        }

        return PageRequest.of(pageNumber, pageSize);
    }

}
