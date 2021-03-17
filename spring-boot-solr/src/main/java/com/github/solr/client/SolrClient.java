package com.github.solr.client;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;

import java.io.IOException;
import java.util.*;


public class SolrClient {
	
//	private static String SOLR_URL = "";
//	public static String CORE_PRODUCT = "product";
//	public static String CORE_POLICY = "policy";
//	public static String CORE_SHOP = "shop";
//	private HttpSolrClient solr = null;
//
////	static {
////		InputStream inputStream = SolrClient.class.getClassLoader().getResourceAsStream("common.properties");
////    	Properties p = new Properties();
////    	try {
////			p.load(inputStream);
////		} catch (IOException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////    	SOLR_URL=p.getProperty("SOLR_URL");
////	}
//
//	public SolrClient(String coreName) {
//        this.getSolr(coreName);
//    }
//
//	private HttpSolrClient getSolr(String coreName) {
//		 solr = new HttpSolrClient(SOLR_URL+coreName);
//	      return solr;
//	    }
//
//
//	public Object getById(String id) throws IOException, SolrServerException {
//		return solr.getById(id);
//	}
//
//	public void addBean(Object obj) throws IOException, SolrServerException {
//		solr.addBean(obj);
//		solr.commit();
//	}
//
//	public void addBeans(List objects) throws SolrServerException, IOException {
//		solr.addBeans(objects);
//		solr.optimize();
//		solr.commit();
//	}
//
//	public void creatAll(List objects) throws SolrServerException, IOException {
//		solr.deleteByQuery("*:*");
//		if( null != objects && objects.size()>0 )
//			solr.addBeans(objects);
//		solr.optimize();
//		solr.commit();
//	}
//
//	public void deleteBeanById(String id) throws SolrServerException, IOException {
//		solr.deleteById(id);
//		solr.optimize();
//		solr.commit();
//	}
//
//	public void deleteBeanByIds(List<String> ids) throws SolrServerException, IOException {
//		solr.deleteById(ids);
//		solr.optimize();
//		solr.commit();
//	}
//
//	public void deleteBeanByQuery(String query) throws SolrServerException, IOException {
//		solr.deleteByQuery(query);
//		solr.optimize();
//		solr.commit();
//	}
//
//	public void updateBean(String id, Object obj) throws SolrServerException, IOException {
//		solr.deleteById(id);
//		solr.addBean(obj);
//		solr.optimize();
//		solr.commit();
//	}
//
//	/**
//     * solr文档集合对象转为普通List
//     * @param docs
//     * @return
//     */
//    public static List<Map<String,Object>> solrDocs2List(SolrDocumentList docs){
//		List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
//		// 循环SolrDocumentList的值
//		if (docs==null) return null;
//		for (SolrDocument solrDocument : docs) {
//			Map<String, Object> dataMap = new HashMap<String, Object>();
//			for(Iterator<Map.Entry<String, Object>> it = solrDocument.iterator(); it.hasNext(); )
//            {
//				Map.Entry<String, Object> docMap = it.next();
//				dataMap.put(docMap.getKey(), docMap.getValue());
//            }
//			dataList.add(dataMap);
//		}
//		return dataList;
//	}
//
//
//	public Map<String,Object> query(Map<String,Object> myParams) throws SolrServerException, IOException {
//		Map<String,Object> returnMap = new HashMap<String,Object>();
//
//		ModifiableSolrParams params = new ModifiableSolrParams();
//		Iterator<String> iterator = myParams.keySet().iterator();
//		/**获取查询参数转为Solr原生参数*/
//		while (iterator.hasNext()){
//			String key = iterator.next();
//			if("pageNo".equals(key)){
//				int pageNo = Integer.valueOf(myParams.get("pageNo").toString()).intValue();
//				if (pageNo<1) pageNo = 1;
//				int pageSize = 10;
//				if (myParams.get("pageSize")!=null){
//					pageSize = Integer.valueOf(myParams.get("pageSize").toString()).intValue();
//				}
//				params.set("start", (pageNo-1) * pageSize);
//				returnMap.put("pageNo", pageNo);
//				returnMap.put("pageSize", pageSize);
//			}else if("pageSize".equals(key)){
//				params.set("rows", new String[]{myParams.get(key).toString()});
//			}else{ //其余solr标准参数
//				if (myParams.get(key) instanceof String){
//					params.set(key, new String[]{myParams.get(key).toString()});
//				}else if (myParams.get(key) instanceof String[]){
//					params.set(key, (String[])(myParams.get(key)));
//				}else if (myParams.get(key) instanceof Boolean){
//					params.set(key, ((Boolean)(myParams.get(key))).booleanValue());
//				}else if (myParams.get(key) instanceof Integer){
//					params.set(key, ((Integer)(myParams.get(key))).intValue());
//				}
//			}
//		}
//
//		QueryResponse resp = solr.query(params);
//		SolrDocumentList docsList = resp.getResults();
//
//		//若开启了高亮，对高亮进行处理，并且默认要传入 "hl.fl"参数（默认以","分隔）
//		if(null != myParams.get("hl") && true == (Boolean)myParams.get("hl")
//				&& null != myParams.get("hl.fl")){
//			Map<String, Map<String, List<String>>> hlMap = resp.getHighlighting();
//			List<String> fieldList = Arrays.asList(myParams.get("hl.fl").toString().replace(" ", "").split(","));
//			if(null != hlMap ){
//				for (SolrDocument sd : docsList) {
//					for (String field : fieldList) {
//						if(null!=hlMap.get(sd.getFieldValue("id").toString()).get(field)){
//							sd.setField(field,hlMap.get(sd.getFieldValue("id").toString()).get(field).toString().substring(1).replaceAll("]$",""));
//						}
//					}
//				}
//			}
//		}
//		/**封装查询结果**/
//		if (myParams.get("resultClass")!=null){ //如果传递了结果类型Class,则根据Annotation封装为指定对象类型
//			returnMap.put("resultList", resp.getBeans((Class)myParams.get("resultClass")));
//		}else{ //如未指定结果类型，则使用通用结果返回
//			List<Map<String,Object>> resultList = solrDocs2List(docsList); //把Solr查询结果数据转为List
//			returnMap.put("resultList", resultList);
//		}
//
//		returnMap.put("facets", resp.getFacetFields()); //分类查询结果
//		if( null != myParams.get("facet.pivot"))
//			returnMap.put("pivotFacet", resp.getFacetPivot().get(myParams.get("facet.pivot").toString().replaceAll("^.*}", ""))); //分类查询结果，多维度
//		returnMap.put("totalSize", (int)docsList.getNumFound());
//		return returnMap;
//	}
//
//	/**
//	 * 重启core
//	 * @param coreName
//	 * @throws SolrServerException
//	 * @throws IOException
//	 */
//	public static void reloadCore(String coreName) throws SolrServerException, IOException {
//		HttpSolrClient solrClient = new HttpSolrClient(SOLR_URL+coreName);
//		CoreAdminRequest.reloadCore(coreName, solrClient);
//	}
//
//	public static void main(String[] args) {
//		int arr[] = new int[5]; //创建int型数组
//        int arr1[] = {1,2,3,4,5};
//        for(int i = 0; i < arr1.length; i++){
//            Arrays.fill(arr,i,i+1,arr1[i]);//循环遍历arr1的值填充到数组arr
//        }
//        for(int j = 0; j < arr.length; j++){
//            //将数组中的元素依次输出
//            System.out.println("第"+j+"个元素是："+arr[j]);
//        }
//	}
}
