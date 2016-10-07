package com.unique.lucence;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.stereotype.Service;

import com.chenlb.mmseg4j.Dictionary;
import com.chenlb.mmseg4j.analysis.ComplexAnalyzer;
import com.unique.core.util.FileUtil;
import com.unique.guide.dao.GuideDao;
import com.unique.guide.po.JbIllness;
import com.unique.guide.po.JbSymptom;
import com.unique.org.po.Dep;
import com.unique.reg.dao.RegDao;
import com.unique.reg.po.Org;
import com.unique.reg.po.Staff;

@Service("lucenceIndexBuilder")
public class LucenceIndexBuilder {
	//搜索引擎索引位置
	public static String LUCENCE_INDEX_PATH = FileUtil.readProperties("comservice.properties", "lucenceIndexPath");
	
	@Resource
	private GuideDao guideDao;
	@Resource
	private RegDao regDao;
	
	
	public void buildIndex(){
		IndexWriter writer = null;
		try {
			/***************创建症状索引*****************/
			List<JbSymptom> symptioms = guideDao.getSymptoms4Lucence(null,null);
			writer = getIndexWriter("symptiom");
			//添加文档
			writer.deleteAll();
			addDoc(writer,symptioms);
			writer.close();
			
			/***************创建疾病索引*****************/
			List<JbIllness> illness = guideDao.getIllness4Lucence(null,null);
			writer = getIndexWriter("illness");
			//添加文档
			writer.deleteAll();
			addDoc(writer,illness);
			writer.close();
			
			/***************创建医院索引*****************/
			List<Org> orgs = regDao.getOrgs4Lucence(null,null);
			writer = getIndexWriter("org");
			//添加文档
			writer.deleteAll();
			addDoc(writer,orgs);
			writer.close();
			
			/***************创建科室索引*****************/
			List<Dep> deps = regDao.getDep4Lucence(null,null);
			writer = getIndexWriter("dep");
			//添加文档
			writer.deleteAll();
			addDoc(writer,deps);
			writer.close();
			
			/***************创建科室索引*****************/
			List<Staff> staffs = regDao.getStaff4Lucence(null,null);
			writer = getIndexWriter("staff");
			//添加文档
			writer.deleteAll();
			addDoc(writer,staffs);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//实例化一个构造器
    private IndexWriter getIndexWriter(String indexName) throws IOException {  
        Directory dir = FSDirectory.open(new File(LUCENCE_INDEX_PATH,indexName)); 
        Analyzer analyzer = new ComplexAnalyzer("");   
        //同时引入了IndexWriterConfig对象，封装了早期版本的一大堆参数  
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_4, analyzer);
        IndexWriter writer = new IndexWriter(dir, config);  
        return writer;
    }
	
	/**
	 * 添加文档
	 * @param indexWriter
	 * @param resultList
	 * @throws IOException
	 */
	private void addDoc(IndexWriter indexWriter, List<?> resultList) throws IOException {
        for (Object vo : resultList) {
            Document doc = createDoc(vo);
            if(doc!=null){
            	indexWriter.addDocument(doc);
            	indexWriter.commit();
            }
        }  
    } 
	
	/**
	 * 添加文档
	 * @param vo 数据对象
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private Document createDoc(Object vo)
			throws UnsupportedEncodingException {
		Document doc = new Document();
		
		if(vo instanceof JbSymptom){
			if(((JbSymptom)vo).getSyName()==null)return null;
			//症状
			doc.add(new StringField("id", ((JbSymptom)vo).getSyId().toString(), Field.Store.YES));
			doc.add(new TextField("syName", ((JbSymptom)vo).getSyName(), Field.Store.YES));
			
			String[] keys = new String[]{"syName"};
			for (int i = 0; i < keys.length; i++) {
				doc.add(new TextField("keyword", doc.get(keys[i]), Field.Store.YES));
			}
		}else if(vo instanceof JbIllness){
			//疾病
			doc.add(new StringField("id", ((JbIllness)vo).getIllId().toString(), Field.Store.YES));
			doc.add(new TextField("illName", ((JbIllness)vo).getIllName(), Field.Store.YES));
			
			String[] keys = new String[]{"illName"};
			for (int i = 0; i < keys.length; i++) {
				doc.add(new TextField("keyword",doc.get(keys[i]), Field.Store.YES));
			}
		}else if(vo instanceof Org){
			//医院
			doc.add(new StringField("id", ((Org)vo).getOrgId().toString(), Field.Store.YES));
			doc.add(new TextField("orgName", ((Org)vo).getOrgName(), Field.Store.YES));
			
			String[] keys = new String[]{"orgName"};
			for (int i = 0; i < keys.length; i++) {
				doc.add(new TextField("keyword", doc.get(keys[i]), Field.Store.YES));
			}
		}else if(vo instanceof Dep){
			//科室
			doc.add(new StringField("id", ((Dep)vo).getDepId().toString(), Field.Store.YES));
			doc.add(new TextField("depName", ((Dep)vo).getDepName(), Field.Store.YES));
			
			String[] keys = new String[]{"depName"};
			for (int i = 0; i < keys.length; i++) {
				doc.add(new TextField("keyword",doc.get(keys[i]), Field.Store.YES));
			}
		}else if(vo instanceof Staff){
			//科室
			doc.add(new StringField("id", ((Staff)vo).getStaffId().toString(), Field.Store.YES));
			doc.add(new TextField("staffName", ((Staff)vo).getStaffName(), Field.Store.YES));
			
			String[] keys = new String[]{"staffName"};
			for (int i = 0; i < keys.length; i++) {
				doc.add(new TextField("keyword", doc.get(keys[i]), Field.Store.YES));
			}
		}

		return doc;
	}
}
