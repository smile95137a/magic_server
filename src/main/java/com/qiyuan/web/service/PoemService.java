package com.qiyuan.web.service;

import com.chl.enums.ExcelColumn;
import com.chl.util.Excel2ObjectParser;
import com.qiyuan.web.dao.PoemMapper;
import com.qiyuan.web.entity.Poem;
import com.qiyuan.web.entity.example.PoemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class PoemService {

    @Autowired
    private PoemMapper poemMapper;

    public void init() {
        List<Poem> poemList = new Excel2ObjectParser()
                .fromFile("C:\\Users\\chloe\\Downloads\\test\\poem01.xlsx")
                .mapColumnToField(ExcelColumn.A, "sequence")
                .mapColumnToFields(ExcelColumn.B, Arrays.asList("title", "zodiac", "fortune"), " ")
                .mapColumnToField(ExcelColumn.C, "poem")
                .mapColumnToField(ExcelColumn.D, "meaning")
                .mapColumnToField(ExcelColumn.E, "dongponote")
                .mapColumnToField(ExcelColumn.F, "bixiannote")
                .mapColumnToField(ExcelColumn.G, "explanation")
                .skipHeader(true)
                .setLineBreakReplacement(ExcelColumn.B, " ")
                .removeLineBreak(ExcelColumn.C, ExcelColumn.D, ExcelColumn.E, ExcelColumn.F)
                .setValueReplacer(ExcelColumn.C, val -> val.replace("、", ",").replace("。", ""))
                .parse(Poem.class);


        IntStream.range(1, poemList.size() + 1)
                .mapToObj(i -> {
                    Poem p = poemList.get(i-1);
                    p.setSort((byte)i);
                    return p;
                }).forEach(p -> poemMapper.insert(p));

    }

    public List<Poem> getPoemList() {
        PoemExample e = new PoemExample();
        e.setOrderByClause("sort ASC");
        return poemMapper.selectByExample(e);
    }

}
