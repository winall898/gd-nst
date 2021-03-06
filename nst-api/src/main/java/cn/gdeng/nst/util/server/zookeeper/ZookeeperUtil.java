package cn.gdeng.nst.util.server.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZookeeperUtil {
  private static Logger logger = LoggerFactory.getLogger(ZookeeperUtil.class);
  private static final int ZK_SESSION_TIME_OUT = 5000;
  
  /**
   * 创建节点
   * @param zks zk服务器(10.17.1.215:2181,10.17.1.216:2181,10.17.1.217:2181)
   * @param nodeName 节点名(/nst_task)
   */
  public static void createNode(String zks, String nodeName){
    ZooKeeper zk = null;
    try {
      zk = new ZooKeeper(zks, ZK_SESSION_TIME_OUT, new Watcher() {
        // 监控所有被触发的事件
        public void process(WatchedEvent event) {
        }
      });
      if(null == zk.exists(nodeName, false)){
        zk.create("/nst_task", "".getBytes(),Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        logger.info("zookeeper 创建节点成功:"+nodeName);
      }else{
        logger.info("zookeeper 创建节点已存在:"+nodeName);
      }
    } catch (Exception e) {
      logger.error("",e);
    }finally{
      try {
        zk.close();
      } catch (InterruptedException e) {
        logger.error("",e);
      }
    }
  }
  
  /**
   * 创建节点
   * @param zks zookeeper集群配置地址
   * @param rootDir 根目录
   * @param nodeName 节点
   * @return
   */
  public static ZooKeeper getRunRight(String zks,String rootDir, String nodeName){
    ZooKeeper zk = null;
    try {
      zk = new ZooKeeper(zks, ZK_SESSION_TIME_OUT, new Watcher() {
        @Override
        public void process(WatchedEvent event) {
        }
      });
      
      //创建根节点
      if(null == zk.exists(rootDir, false)){
        zk.create(rootDir, "".getBytes(),Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        logger.info("zookeeper 根节点创建成功:"+rootDir);
      }else{
        logger.info("zookeeper 根节点已存在:"+rootDir);
      }
      //创建子节点
      if(null == zk.exists(rootDir+nodeName, false)){
        zk.create(rootDir+nodeName, "".getBytes(),Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        logger.info("zookeeper 获取执行权限, 子节点创建成功:"+rootDir+nodeName);
        return zk;
      }else{
        logger.info("zookeeper 子节点节点已存在:"+rootDir+nodeName);
        zk.close();
      }
    } catch (Exception e) {
      try {
        if(null != zk){
          zk.close();
        }
      } catch (InterruptedException e1) {
        logger.error("",e);
      }
      logger.error("",e);
    }
    return null;
  }
  
  public static void main(String[] args) throws Exception {
    ZooKeeper zk = ZookeeperUtil.getRunRight("10.17.1.215:2181,10.17.1.216:2181,10.17.1.217:2181","/nst-dev-task", "/advertise");
    if(null != zk){
      zk.close();
    }
  }

}
