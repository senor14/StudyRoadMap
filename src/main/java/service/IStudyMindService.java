package service;

import domain.StudyMindData;
import domain.StudyMindNodeData;

import java.util.List;

public interface IStudyMindService {

    public List<StudyMindData> getMindMapData(StudyMindData mindData) throws Exception;

    public List<StudyMindNodeData> getMindMapNode(StudyMindNodeData nodeData) throws Exception;

    public int insertMindData(StudyMindData mindData) throws Exception;

    public int insertNodeData(StudyMindNodeData nodeData) throws Exception;

    public StudyMindData getMindMapInfoByMindId(String mindId) throws Exception;

    public StudyMindNodeData getMindMapNodeByMindId(String mindId) throws Exception;

    public int updateMindData(StudyMindData mindData) throws Exception;

    public int updateNodeData(StudyMindNodeData nodeData) throws Exception;

    public int updateNodePosition(StudyMindNodeData position) throws Exception;

    public int deleteMindData(String mindId) throws Exception;

    public int deleteMindNodeData(String mindId) throws Exception;

    public List<StudyMindData> getMindDataByRoadNodeId(String roadNodeId) throws Exception;

    public List<StudyMindNodeData> getMindNodeByRoadNodeId(String roadNodeId) throws Exception;


}
