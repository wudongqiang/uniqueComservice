package com.unique.lucence;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Service;

import com.chenlb.mmseg4j.ComplexSeg;
import com.chenlb.mmseg4j.Dictionary;
import com.chenlb.mmseg4j.MMSeg;
import com.chenlb.mmseg4j.Seg;
import com.chenlb.mmseg4j.SimpleSeg;
import com.chenlb.mmseg4j.Word;
import com.chenlb.mmseg4j.analysis.ComplexAnalyzer;
import com.sun.istack.internal.NotNull;
import com.unique.core.annotation.HttpMethod;
import com.unique.guide.dao.GuideDao;
import com.unique.guide.po.JbIllness;
import com.unique.guide.po.JbSymptom;
import com.unique.org.dao.OrgDao;
import com.unique.org.po.Dep;
import com.unique.reg.dao.RegDao;
import com.unique.reg.po.Org;
import com.unique.reg.po.Staff;

@Service("lucenceQuery")
public class LucenceQuery {
	@Resource
	private RegDao regDao;
	@Resource
	private OrgDao orgDao;
	@Resource
	private GuideDao guideDao;
	
	@HttpMethod
	public HashMap<String, Object> query(@NotNull String keyword,@NotNull String type){
		System.out.println(keyword + " " + type);
		HashMap<String, Object> result = new HashMap<String, Object>();
		Analyzer analyzer = new ComplexAnalyzer();
		QueryParser parser = new QueryParser("keyword", analyzer);
		try {
			Query query = parser.parse(keyword);
			
			//打开索引目录
			Directory dir = FSDirectory.open(new File(LucenceIndexBuilder.LUCENCE_INDEX_PATH,type));
			IndexReader reader = IndexReader.open(dir);
			IndexSearcher searcher = new IndexSearcher(reader);
			TopDocs hits = searcher.search(query, 50000);
			//找到记录数
			result.put("count", hits.totalHits);
			System.out.println("共找到" + hits.totalHits + "条记录");
			
			ScoreDoc[] docs = hits.scoreDocs;
			String[] ids = null;
			if(docs.length>0){
				ids = new String[docs.length];
				for(int i = 0; i < docs.length; i++){
					int docId = docs[i].doc;
					Document doc = searcher.doc(docId);
					ids[i] = doc.get("id");
				}
				
				if("symptiom".equals(type)){
					//先获取主症状
					if(ids!=null && ids.length >0){
						List<JbSymptom> list = guideDao.getBsSyByZzz(ids);
						result.put("symptiom", list);
						result.put("zsid", ids);
					}
				}else if("illness".equals(type)){
					List<JbIllness> list = guideDao.getIllness4Lucence(null, ids);
					result.put("illness", list);
				}else if("org".equals(type)){
					List<Org> list = regDao.getOrgs4Lucence(null, ids);
					result.put("org", list);
				}else if("dep".equals(type)){
					List<Dep> list = regDao.getDep4Lucence(null, ids);
					result.put("dep", list);
				}else if("staff".equals(type)){
					List<Staff> list = regDao.getStaff4Lucence(null, ids);
					result.put("staff", list);
				}
			}
			
			reader.close();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
//	public static void main(String[] args) throws Exception {
//		//分词器
//		Analyzer analyzer = new SimpleAnalyzer();
//		SimpleAnalyzer s = new SimpleAnalyzer();
//		//解析器
//		QueryParser parser = new QueryParser("partName", analyzer);
//		Query query = parser.parse("我头部痛");
//		
//		doSearch(query);
//	}
	
//	public static void main(String[] args) throws IOException {
//		ComplexAnalyzer analyzer = new ComplexAnalyzer("F:\\yuyou\\JAR\\mmseg4j-1.9.1\\mmseg4j-1.9.1\\mmseg4j-core\\src\\main\\resources\\data\\"); //定义一个解析器  
//	    String text = "我脑壳痛"; 
//	    TokenStream tokenStream = analyzer.tokenStream(text, new StringReader(text)); //得到token序列的输出流  
//
//	    boolean hasnext= tokenStream.incrementToken();
//	    while (hasnext) {
//			//AttributeImpl ta = new AttributeImpl();
//			CharTermAttribute ta = tokenStream.getAttribute(CharTermAttribute.class);
//			//TermAttribute ta = ts.getAttribute(TermAttribute.class);
//			
//			System.out.println(ta.toString());
//			hasnext = tokenStream.incrementToken();
//		}
//
//	}
	
	public String segStr(String text,String mode) throws IOException{         
	    String returnStr = "";  
	    Seg seg = null;  
	    java.util.Properties p = System.getProperties();  
	    p.setProperty("mmseg.dic.path", "C:\\dic");  
	    Dictionary dic = Dictionary.getInstance();  
	      
	    if ("simple".equals(mode)) {  
	        seg = new SimpleSeg(dic);  
	    } else {  
	        seg = new ComplexSeg(dic);  
	    }  
	  
	    MMSeg mmSeg = new MMSeg(new InputStreamReader(new ByteArrayInputStream(text.getBytes())), seg);  
	    Word word = null;         
	    while ((word = mmSeg.next()) != null) {  
	        //切词为单字的无实际意思，所以词组做为返回结果，仅仅为测试  
	        if (word.getString().length()>=2){  
	            returnStr += word.getString()+" ";  
	        }  
	    }         
	      
	    return returnStr;  
	}
}
