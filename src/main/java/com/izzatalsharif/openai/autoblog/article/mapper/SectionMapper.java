package com.izzatalsharif.openai.autoblog.article.mapper;

import com.izzatalsharif.openai.autoblog.article.dto.SectionDTO;
import com.izzatalsharif.openai.autoblog.article.dto.agent.SectionOutline;
import com.izzatalsharif.openai.autoblog.article.model.Section;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SectionMapper {

    SectionMapper MAPPER = Mappers.getMapper(SectionMapper.class);

    Section toSection(SectionDTO sectionDTO);

    SectionDTO toSectionDTO(Section section);

    @Mapping(target = "title", source = "sectionOutline.title")
    @Mapping(target = "content", source = "content")
    SectionDTO toSectionDTO(SectionOutline sectionOutline, String content);

}
