package com.github.solr.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.solr.client.solrj.*;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.params.SolrParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * Created by alex on 2018/6/13.
 */
@Component
@Slf4j
public class CustomClient {

    @Autowired
    private SolrClient client;

    public Map<String, Object> query(String collection, Map<String, Object> myParams) throws SolrServerException, IOException {
        Map<String, Object> returnMap = new HashMap<String, Object>();

        ModifiableSolrParams params = new ModifiableSolrParams();
        Iterator<String> iterator = myParams.keySet().iterator();
        /**获取查询参数转为Solr原生参数*/
        while (iterator.hasNext()) {
            String key = iterator.next();
            if ("pageNo".equals(key)) {
                int pageNo = Integer.valueOf(myParams.get("pageNo").toString()).intValue();
                if (pageNo < 1) pageNo = 1;
                int pageSize = 10;
                if (myParams.get("pageSize") != null) {
                    pageSize = Integer.valueOf(myParams.get("pageSize").toString()).intValue();
                }
                params.set("start", (pageNo - 1) * pageSize);
                returnMap.put("pageNo", pageNo);
                returnMap.put("pageSize", pageSize);
            } else if ("pageSize".equals(key)) {
                params.set("rows", new String[]{myParams.get(key).toString()});
            } else if ("fq".equals(key)) {
                params.add((SolrParams) myParams.get("fq"));
            } else { //其余solr标准参数
                if (myParams.get(key) instanceof String) {
                    params.set(key, new String[]{myParams.get(key).toString()});
                } else if (myParams.get(key) instanceof String[]) {
                    params.set(key, (String[]) (myParams.get(key)));
                } else if (myParams.get(key) instanceof Boolean) {
                    params.set(key, ((Boolean) (myParams.get(key))).booleanValue());
                } else if (myParams.get(key) instanceof Integer) {
                    params.set(key, ((Integer) (myParams.get(key))).intValue());
                }
            }
        }

        QueryResponse resp = client.query(collection, params);
        SolrDocumentList docsList = resp.getResults();
        log.debug("solr query : " + resp.getResponseHeader());
//        log.debug("solr query total: " + resp.toString());

        //若开启了高亮，对高亮进行处理，并且默认要传入 "hl.fl"参数（默认以","分隔）
        if (null != myParams.get("hl") && true == (Boolean) myParams.get("hl")
                && null != myParams.get("hl.fl")) {
            Map<String, Map<String, List<String>>> hlMap = resp.getHighlighting();
            List<String> fieldList = Arrays.asList(myParams.get("hl.fl").toString().replace(" ", "").split(","));
            if (null != hlMap) {
                for (SolrDocument sd : docsList) {
                    for (String field : fieldList) {
                        if (null != hlMap.get(sd.getFieldValue("id").toString()).get(field)) {
                            sd.setField(field, hlMap.get(sd.getFieldValue("id").toString()).get(field).toString().substring(1).replaceAll("]$", ""));
                        }
                    }
                }
            }
        }
        /**封装查询结果**/
        if (myParams.get("resultClass") != null) { //如果传递了结果类型Class,则根据Annotation封装为指定对象类型
            returnMap.put("resultList", resp.getBeans((Class) myParams.get("resultClass")));
        } else { //如未指定结果类型，则使用通用结果返回
            List<Map<String, Object>> resultList = solrDocs2List(docsList); //把Solr查询结果数据转为List
            returnMap.put("resultList", resultList);
        }

        returnMap.put("facets", coverFacetFields(resp.getFacetFields())); //分类查询结果
        if (null != myParams.get("facet.pivot"))
            returnMap.put("pivotFacet", resp.getFacetPivot().get(myParams.get("facet.pivot").toString().replaceAll("^.*}", ""))); //分类查询结果，多维度
        returnMap.put("totalSize", (int) docsList.getNumFound());
        return returnMap;
    }

    private Map<String,Map<String,Long>> coverFacetFields(List<FacetField> facetFields){
        Map<String,Map<String,Long>> result = new HashMap<>();
        if(facetFields==null || facetFields.isEmpty())
            return result;
        for (FacetField field:
                facetFields) {
            Map<String,Long> _temp = new HashMap<>();
            for (FacetField.Count count:
                    field.getValues()) {
                _temp.put(count.getName(),count.getCount());
            }
            result.put(field.getName(),_temp);
        }
        return result;
    }


    /**
     * solr文档集合对象转为普通List
     *
     * @param docs
     * @return
     */
    public static List<Map<String, Object>> solrDocs2List(SolrDocumentList docs) {
        List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
        // 循环SolrDocumentList的值
        if (docs == null) return null;
        for (SolrDocument solrDocument : docs) {
            Map<String, Object> dataMap = new HashMap<String, Object>();
            for (Iterator<Map.Entry<String, Object>> it = solrDocument.iterator(); it.hasNext(); ) {
                Map.Entry<String, Object> docMap = it.next();
                dataMap.put(docMap.getKey(), docMap.getValue());
            }
            dataList.add(dataMap);
        }
        return dataList;
    }


    public void addBean(String collection, Object obj) throws IOException, SolrServerException {
        client.addBean(collection, obj);
        client.commit(collection);
    }

    public synchronized void addBeans(String collection, List objects) throws SolrServerException, IOException {
        client.addBeans(collection, objects);
        client.commit(collection);
    }

    public void deleteBeanById(String collection, String id) throws SolrServerException, IOException {
        client.deleteById(collection, id);
        client.commit(collection);
    }

    public void deleteBeanByIds(String collection, List<String> ids) throws SolrServerException, IOException {
        client.deleteById(collection, ids);
        client.commit(collection);
    }

    public void deleteBeanByQuery(String collection, String query) throws SolrServerException, IOException {
        client.deleteByQuery(collection, query);
        client.commit(collection);
    }

    public void optimize(String collection) throws SolrServerException, IOException {
        client.optimize(collection);
    }
}
