package top.sharehome.springbootinittemplate.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.sharehome.springbootinittemplate.model.entity.ChatConversation;

@Mapper
public interface ChatConversationMapper extends BaseMapper<ChatConversation> {
}
