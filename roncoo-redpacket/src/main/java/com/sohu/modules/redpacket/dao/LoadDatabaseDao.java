package com.sohu.modules.redpacket.dao;

import java.util.List;
import java.util.Map;

public interface LoadDatabaseDao<T> {

	void save(String listKeyName,List<T> receiveListMap);
}
