package com.izzatalsharif.openai.autoblog.mapper;

import com.izzatalsharif.openai.autoblog.dto.SectionDTO;
import com.izzatalsharif.openai.autoblog.dto.SectionOutline;
import com.izzatalsharif.openai.autoblog.dto.WriterPrompt;
import com.izzatalsharif.openai.autoblog.model.Section;
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

    @Mapping(target = ".", source = "sectionOutline")
    @Mapping(target = "keywords", source = "keywords")
    WriterPrompt toWriterPrompt(SectionOutline sectionOutline, String keywords);

}
