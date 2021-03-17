package com.github.solr.repository;

import com.github.solr.bean.ProductCore;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by alex on 2018/6/13.
 */
@Repository
public interface ProductRepository extends SolrCrudRepository<ProductCore,String>{

}
