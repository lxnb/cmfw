package com.baizhi.indexDao;


import com.baizhi.entity.Chapter;
import com.baizhi.util.LuceneUtil;
import com.baizhi.util.SimpledateformatUtil;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LuceneProductDao {

    public void createIndex(Chapter chapter) {
        IndexWriter indexWriter = LuceneUtil.getIndexWriter();
        Document docFromPro = getDocFromPro(chapter);
        try {
            System.out.println("这是创建索引时的对象内容" + docFromPro);
            indexWriter.addDocument(docFromPro);
            LuceneUtil.commit(indexWriter);
        } catch (IOException e) {
            e.printStackTrace();
            LuceneUtil.rollback(indexWriter);
        }
    }

    public void deleteIndex(String id) {
        IndexWriter indexWriter = LuceneUtil.getIndexWriter();
        try {
            indexWriter.deleteDocuments(new Term("id", id));
            LuceneUtil.commit(indexWriter);
        } catch (IOException e) {
            e.printStackTrace();
            LuceneUtil.rollback(indexWriter);
        }

    }

    public void updateIndex(Chapter chapter) {
        IndexWriter indexWriter = LuceneUtil.getIndexWriter();
        Document docFromPro = getDocFromPro(chapter);
        try {
            indexWriter.updateDocument(new Term("id", String.valueOf(chapter.getId())), docFromPro);
            LuceneUtil.commit(indexWriter);
        } catch (IOException e) {
            e.printStackTrace();
            LuceneUtil.rollback(indexWriter);
        }
    }

    public List<Chapter> SearcherIndex(String params) {
        IndexSearcher indexSearcher = LuceneUtil.getIndexSearcher();
        List<Chapter> list = null;
        String[] strs = {"size", "title", "uploadDate"};
        //根据IK分词器多段查询
        MultiFieldQueryParser multiFieldQueryParser = new MultiFieldQueryParser(Version.LUCENE_44, strs, new IKAnalyzer());
        try {
            //创建查询对象
            Query query = multiFieldQueryParser.parse(params);
            //设置高亮格式
            Formatter formatter = new SimpleHTMLFormatter("<font color='red'>", "</font>");
            //哪些词高亮
            Scorer scorer = new QueryScorer(query);
            Highlighter highlighter = new Highlighter(formatter, scorer);
            //封装了符合条件的查询结果
            TopDocs topDocs = indexSearcher.search(query, 100);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;//分数从高到低排序后的数组
            list = new ArrayList<>();
            for (int i = 0; i < scoreDocs.length; i++) {
                ScoreDoc scoreDoc = scoreDocs[i];//封装了分数,索引文章编号..
                int doc = scoreDoc.doc;//索引文章编号
                Document document = indexSearcher.doc(doc);
                Chapter chapter = getProFromDoc(document, highlighter);
                list.add(chapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Document getDocFromPro(Chapter chapter) {
        Document document = new Document();
        String s = SimpledateformatUtil.DateToString(chapter.getUploadDate());
        document.add(new IntField("id", chapter.getId(), Field.Store.YES));
        document.add(new TextField("title", chapter.getTitle(), Field.Store.YES));
        document.add(new StringField("size", chapter.getSize(), Field.Store.YES));
        document.add(new StringField("duration", chapter.getDuration(), Field.Store.YES));
        document.add(new StringField("uploadDate", s, Field.Store.YES));
        document.add(new StringField("albumId", chapter.getAlbumId(), Field.Store.YES));
        document.add(new StringField("url", chapter.getUrl(), Field.Store.YES));
        System.out.println(document);
        return document;
    }

    public Chapter getProFromDoc(Document document, Highlighter highlighter) {
        Chapter chapter = null;
        try {
            chapter = new Chapter();
            chapter.setId(Integer.valueOf(document.get("id")));
            chapter.setAlbumId(document.get("albumId"));
            chapter.setTitle(highlighter.getBestFragment(new IKAnalyzer(), "title", document.get("title")));
            chapter.setUrl(document.get("url"));
            chapter.setDuration(document.get("duration"));
            chapter.setSize(document.get("size"));
            Date upTime = SimpledateformatUtil.StringToDate(document.get("uploadDate"));
            chapter.setUploadDate(upTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chapter;
    }

}
