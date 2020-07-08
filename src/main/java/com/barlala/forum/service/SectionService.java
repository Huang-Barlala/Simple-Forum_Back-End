package com.barlala.forum.service;

import com.barlala.forum.dao.SectionMapper;
import com.barlala.forum.entity.Section;
import com.barlala.forum.entity.SectionExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Barlala
 * @version 1.0
 * @date 2020/6/23 下午4:01
 */
@Service
public class SectionService {
    private final SectionMapper sectionMapper;
    public List<Section> sectionList = new ArrayList<>();

    @Autowired
    public SectionService(SectionMapper sectionMapper) {
        this.sectionMapper = sectionMapper;
    }

    public void updateSectionList() {
        SectionExample example = new SectionExample();
        sectionList = sectionMapper.selectByExample(example);
    }

    public boolean isSectionInSectionList(int sectionId) {
        if (sectionList.isEmpty()) {
            updateSectionList();
        }
        for (Section section : sectionList) {
            if (section.getId() == sectionId) {
                return true;
            }
        }
        return false;
    }

    public List<Section> getSections() {
        if (sectionList.isEmpty()) {
            updateSectionList();
        }
        return sectionList;
    }

    public boolean updateSection(Section section) {
        int result = sectionMapper.updateByPrimaryKey(section);
        this.updateSectionList();
        return result == 1;
    }

    public boolean insertSection(Section section) {
        int result = sectionMapper.insert(section);
        this.updateSectionList();
        return result == 1;
    }

    public boolean deleteSection(int id) {
        int result = sectionMapper.deleteByPrimaryKey(id);
        this.updateSectionList();
        return result == 1;
    }
}
