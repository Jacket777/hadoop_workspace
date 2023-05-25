package com.cnsuning.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.permission.*;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.protocol.*;
import org.apache.hadoop.security.Credentials;
import org.apache.hadoop.security.token.Token;
import org.apache.hadoop.util.Progressable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class DfsApiTest {
    private static final Logger logger = LoggerFactory.getLogger("dfsApiTest");
    private final DistributedFileSystem dfs;
    private final Path rootPath;
    private short replication;
    //dfs.namenode.fs-limits.min-block-size:1048576
    private long blockSize = 1048576;
    private int bufferSize = 1024;
    private Progressable progressable;
    private FsPermission fsPermission;
    private EnumSet<CreateFlag> flags;
    private Configuration conf;
    private DfsTestUtil util;
    private List<AclEntry> aclList;
    private List<AclEntry> aclList1;
    private String renewer = "dfs";
    private PathFilter pathFilter;


    public DfsApiTest(DistributedFileSystem dfs, Path path) {
        this.dfs = dfs;
        this.rootPath = path;
    }


    public void initParam() throws IOException {
        conf = new Configuration();
        replication = 1;
        //need rewrite?
        progressable = new Progressable() {
            @Override
            public void progress() {
                System.out.println("=====");
            }
        };
        //storage some state
        flags = EnumSet.of(CreateFlag.CREATE, CreateFlag.OVERWRITE);

        util = new DfsTestUtil(dfs, rootPath);

        AclEntry aclEntry = new AclEntry.Builder().setName("user1").setPermission(FsAction.ALL).
                setScope(AclEntryScope.DEFAULT).setType(AclEntryType.USER).build();
        AclEntry aclEntry1 = new AclEntry.Builder().setName("group1").setPermission(FsAction.ALL).
                setScope(AclEntryScope.DEFAULT).setType(AclEntryType.GROUP).build();
        AclEntry aclEntry2 = new AclEntry.Builder().setPermission(FsAction.ALL).
                setScope(AclEntryScope.DEFAULT).setType(AclEntryType.MASK).build();
        AclEntry aclEntry3 = new AclEntry.Builder().setPermission(FsAction.ALL).
                setScope(AclEntryScope.DEFAULT).setType(AclEntryType.OTHER).build();

        AclEntry aclEntry4 = new AclEntry.Builder().setName("user2").setPermission(FsAction.READ).
                setScope(AclEntryScope.ACCESS).setType(AclEntryType.USER).build();
        AclEntry aclEntry5 = new AclEntry.Builder().setName("group2").setPermission(FsAction.READ).
                setScope(AclEntryScope.ACCESS).setType(AclEntryType.GROUP).build();
//        AclEntry aclEntry6 = new AclEntry.Builder().setPermission(FsAction.READ).
//                setScope(AclEntryScope.DEFAULT).setType(AclEntryType.MASK).build();
        AclEntry aclEntry7 = new AclEntry.Builder().setPermission(FsAction.READ).
                setScope(AclEntryScope.ACCESS).setType(AclEntryType.OTHER).build();


        aclList = new ArrayList<>();
        aclList.add(aclEntry);
        aclList.add(aclEntry1);
        aclList.add(aclEntry2);
        aclList.add(aclEntry3);


        aclList1 = new ArrayList<>();
        aclList1.add(aclEntry4);
        aclList1.add(aclEntry5);
//        aclList1.add(aclEntry6);
        aclList1.add(aclEntry7);


        pathFilter = new PathFilter() {
            @Override
            public boolean accept(Path path) {
                return false;
            }
        };
        fsPermission = FsPermission.getDefault();

    }

    public void testLoad() throws IOException, URISyntaxException {
        initParam();
        //test put
//        testCopyFromLocalFile();
        //testCopyFromLocalFile1();
        //testCopyFromLocalFile2();
        //delete local files.Path is files
        //test download
//        testCopyToLocalFile();
        //testCopyToLocalFile1();
        //会删除本地文件
//        testCopyToLocalFile2();
        //testMoveFromLocalFile();
    }


    public void test() throws IOException, URISyntaxException {
        initParam();
        testRemoveAclEntries();
//        testModifyAclEntries();
        testAddDelegationTokens();
        testEmptyDelegationToken();

        System.out.println("=====================================>");
    }


    private void testExists() throws IOException {
        Path path = util.createTmpFile("testExit");
        dfs.exists(path);
        logger.info("testExit:" + dfs.exists(path));
    }

    //look
    private void testRoll() throws IOException {
        dfs.rollEdits();
//        dfs.rollingUpgrade(HdfsConstants.RollingUpgradeAction.FINALIZE);
    }

    //find file
    private void testMetaSave() throws IOException {
        Path path = util.createTmpFile("testMetaSave");
        dfs.metaSave("testMetaSave");
    }


    private void testFinalizeUpgrade() throws IOException {
        dfs.finalizeUpgrade();
    }

    private void testIsInSafeMode() throws IOException {
        logger.info("testIsInSafeMode" + dfs.isInSafeMode());
    }

    private void testCacheRelative() throws IOException {
        Path path = util.createTmpFile("testListCacheDirectives");
        Path path1 = util.createTmpFile("testListCacheDirectives1");

        CachePoolInfo cachePoolInfo = new CachePoolInfo("cachePool");

        //dfs.removeCachePool("cachePool");
        dfs.addCachePool(cachePoolInfo);
        CacheDirectiveInfo info = new CacheDirectiveInfo.Builder().
                setExpiration(CacheDirectiveInfo.Expiration.newAbsolute(10000)).setPath(path).
                setPool("cachePool").setReplication((short) 1).build();
        CacheDirectiveInfo info1 = new CacheDirectiveInfo.Builder().
                setExpiration(CacheDirectiveInfo.Expiration.newAbsolute(10000)).setPath(path1).
                setPool("cachePool").setReplication((short) 1).build();

        logger.info("addCacheDirective" + dfs.addCacheDirective(info));
        EnumSet<CacheFlag> flags = EnumSet.of(CacheFlag.FORCE);
        long info1Id = dfs.addCacheDirective(info1, flags);
        logger.info("addCacheDirective(info,flags)" + info1Id);
        dfs.listCacheDirectives(info);
        RemoteIterator<CachePoolEntry> it = dfs.listCachePools();
        while (it.hasNext()) {
            logger.info("addCacheDirective(info,flags)" + it.next().getInfo().getPoolName());
        }
        RemoteIterator<CacheDirectiveEntry> remoteIterator = dfs.listCacheDirectives(null);
        long id = 0;
        if (remoteIterator.hasNext()) {
            CacheDirectiveEntry ca = remoteIterator.next();
            CacheDirectiveInfo temp = ca.getInfo();
            dfs.modifyCacheDirective(temp);
            dfs.modifyCacheDirective(temp, flags);
                       id = temp.getId();
            logger.info("modifyCacheDirective ------CacheDirectiveInfo;" + temp.getPath());
        }

        //manul check
        dfs.modifyCachePool(cachePoolInfo);
        Path path3 = util.createTmpDir("testListCacheDirectives");
        dfs.modifyAclEntries(path3, aclList);
        dfs.removeCacheDirective(id);
        dfs.removeCachePool("cachePool");
    }


    private void testAddDelegationTokens() throws IOException {
        Credentials credentials = new Credentials();
        Token<?>[] token = dfs.addDelegationTokens(renewer, credentials);
        logger.info("token.length" + token.length);
        if (token.length > 0) {
            logger.info("token.length" + token[0].getIdentifier().toString());
            logger.info("token.length" + token[0].getKind().toString());
            logger.info("token.length" + token[0].getPassword().toString());
        }
    }

    private void testInitialize() throws URISyntaxException, IOException {
        URI uri = dfs.getUri();
        dfs.initialize(uri, conf);

        logger.info("testInitialize" + dfs);
        logger.info("testInitialize" + dfs);
    }

    private void testGlobStatus() throws IOException {
        //先自建立一个文件
        Path path = new Path("/home/bigdata/tmp/**");
        FileStatus[] fileStatuses = dfs.globStatus(path);
        logger.info("testGlobStatus" + fileStatuses.length);
    }

    private void testCreateSymlink() throws IOException {
        Path src = util.createTmpDir("testCreateSymlink_src");
        Path dst = util.createTmpDir("testCreateSymlink_dst");
        dfs.createSymlink(src, dst, false);
    }

    private void testMakeQualified() throws IOException {
        Path path = util.createTmpFile("testMakeQualified");
        logger.info("testMakeQualified:" + dfs.makeQualified(path));
        Path path1 = new Path("/home/bigdata/*&^");
        //throw 异常
        logger.info("testMakeQualified1:" + dfs.makeQualified(path1));
    }

    private void testresolvePath() throws IOException {
        //生成文件

        Path path1 = new Path("/home/bigdata/..");
        //返回的应该是/home下的文件
        logger.info("testresolvePath    :" + dfs.resolvePath(path1).toString());

    }

    private void testCompleteLocalOutput() throws IOException {
        //生成本地文件
        Path tmp = util.createLocalTmpFile("testCompleteLocalOutput_localFile_tmp");
        //dfs上目录
        Path dst = util.createTmpDir("testCompleteLocalOutput_dfsDir_dst");
        dfs.completeLocalOutput(dst, tmp);

    }

    public void testConcat() throws IOException {
        Configuration conf = dfs.getConf();
        DfsTestUtil util = new DfsTestUtil(dfs, rootPath);
        String str = "testConcat_dst";
        Path s = new Path("/home/bigdata/temp1/testConcat_dst/1");
        //String d="/home/bigdata/temp1/testConcat_dst";
        dfs.copyFromLocalFile(s, s);
        Path dst1 = util.createTmpFiles(str, "dst", dfs);
        Path dst2 = util.createTmpFiles(str, "dst1", dfs);
        logger.info("testConcat: conf" + conf.get("dfs.block.size"));
        dfs.concat(s, new Path[]{dst1, dst2});
    }

    public void testConcat2(String s, String d1, String d2) throws IOException {
//        Path src=new Path(s);
//        Path dst1=new Path(d1);
//        Path dst2=new Path(d2);
//        dfs.concat(src, new Path[]{dst1, dst2});
        Path src = new Path("/a");
        Path dst1 = new Path("/b");
        Path dst2 = new Path("/c");
        dfs.concat(src, new Path[]{dst1, dst2});
    }


    public void testConcat1() throws IOException {
        Configuration conf = dfs.getConf();
        conf.set("dfs.block.size", "8");
        DfsTestUtil util = new DfsTestUtil(dfs, rootPath);
        String str = "testConcat_dst";
        Path src = util.createTmpFiles(str, "src", dfs);
        Path dst1 = util.createTmpFiles(str, "dst", dfs);
        Path dst2 = util.createTmpFiles(str, "dst1", dfs);
        logger.info("testConcat: conf" + conf.get("dfs.block.size"));
        dfs.concat(src, new Path[]{dst1, dst2});
    }

    private void testCancelDeleteOnExit() throws IOException {
        Path path = util.createTmpFile("testCancelDeleteOnExit");
        dfs.cancelDeleteOnExit(path);
    }

    private void testMoveToLocalFile() throws IOException {
        Path src = util.createTmpFile("testMoveToLocalFile_src");
        Path dst = util.createLocalTmpDir("testMoveToLocalFile_dst");
        dfs.moveToLocalFile(src, dst);
    }

    private void testCreateNewFile() throws IOException {
        Path dirPath = util.createTmpDir("testCreateNewFile");
        Path path = new Path(dirPath, "file_forTestCreateNew");
        boolean flag = dfs.createNewFile(path);
        logger.info("testCreateNewFile:     " + flag);
    }


    private void testGlobStatus1() throws IOException {
        logger.info("testGlobStatus1    pathFilter is false");


        Path path = util.createTmpFile("testGlobStatus1");
        PathFilter pathFilter2 = new PathFilter() {
            @Override
            public boolean accept(Path path) {
                return false;
            }
        };


        FileStatus[] fileStatuses = dfs.globStatus(path, pathFilter2);
        if (fileStatuses != null) {
            logger.info("testGlobStatus1=========" + fileStatuses.length);
        }

        logger.info("testGlobStatus1    pathFilter is true");


        PathFilter pathFilter3 = new PathFilter() {
            @Override
            public boolean accept(Path path) {
                return true;
            }
        };


        FileStatus[] fileStatuses2 = dfs.globStatus(path, pathFilter3);
        logger.info("testGlobStatus1=========" + fileStatuses2.length);
    }

    public void testRemoveAclEntries() throws IOException {
        Path path = util.createTmpDir("removeAclEntries");
       // Path path1 = util.createTmpDir("removeAclEntries1");
        dfs.modifyAclEntries(path, aclList);
        AclStatus aclStatus1 = dfs.getAclStatus(path);
        logger.info("Before remove AclEntries:  " + aclStatus1.toString());
        logger.info("=====================remove=============================");
        dfs.removeAclEntries(path, aclList);
        AclStatus aclStatus = dfs.getAclStatus(path);
        logger.info("After removeAclEntries:      " + aclStatus.toString());
    }

    public void testRemoveDefaultAcl() throws IOException {
        Path path = util.createTmpFile("removeDefaultAcl");
        dfs.removeDefaultAcl(path);
        AclStatus aclStatus = dfs.getAclStatus(path);

        logger.info("removeDefaultAcl:      " + aclStatus.toString());
    }

    public void testAllGet() throws IOException {
        Path path = util.createTmpFile("testAllGet");

        logger.info("getAclStatus:  owner    " + dfs.getAclStatus(path).getOwner().toString() +
                "getAclStatus:  group    " + dfs.getAclStatus(path).getGroup().toString());
        logger.info("getConf:      " + dfs.getConf().toString());
        logger.info("getContentSummary:      " + dfs.getContentSummary(path).toString());
        logger.info("getCanonicalServiceName:      " + dfs.getCanonicalServiceName());
        dfs.getDelegationToken(renewer);
        //logger.info("getDelegationToken:      " + dfs.getDelegationToken(renewer).toString());
        logger.info("getDefaultBlockSize:      " + dfs.getDefaultBlockSize());
        logger.info("getDefaultReplication:      " + dfs.getDefaultReplication());


        //always return null
        dfs.getChildFileSystems();
        //long length, boolean isdir, int block_replication,long blocksize, long modification_time, Path path
        FileStatus status = new FileStatus(10, false, 3,
                512, 0, path);
        dfs.getFileBlockLocations(path, 0, 3);
        dfs.getFileBlockLocations(status, 0, 3);
        logger.info("getFileStatus:  blocksize  " + dfs.getFileStatus(path).getBlockSize() +
                "getFileStatus: Path   " + dfs.getFileStatus(path).getPath());
        logger.info("getFileChecksum:      " + dfs.getFileChecksum(path).getBytes().toString());
        logger.info("getFileLinkStatus:      " + dfs.getFileLinkStatus(path).toString());
        logger.info("getHomeDirectory:      " + dfs.getHomeDirectory().toString());


        //unSupport
//        dfs.createSymlink(path1,path,false);
//        dfs.getLinkTarget(path);
//        dfs.getLinkTarget(path);

        logger.info("getCanonicalServiceName:      " + dfs.getCanonicalServiceName().toString());
        logger.info("getWorkingDirectory:      " + dfs.getWorkingDirectory().toString());
        logger.info("getStatus:      " + dfs.getStatus().toString());
        logger.info("getStatus(path):      " + dfs.getStatus(path).toString());
        logger.info("getScheme:      " + dfs.getScheme().toString());
        logger.info("getUsed:      " + dfs.getUsed());
        logger.info("getUri:      " + dfs.getUri());
    }

    public void testAllSet() throws IOException {
        Path aclDir = util.createTmpDir("testSetAcl_dir");
        dfs.setAcl(aclDir, aclList);

        Path path = util.createTmpFile("testAllSet");
        String username = "api";
        String groupname = "test";
        dfs.setOwner(path, username, groupname);
        dfs.setPermission(path, fsPermission);
        dfs.setReplication(path, (short) 3);
        dfs.setVerifyChecksum(false);

        //Access time for hdfs is not configured.
        // Please set dfs.namenode.accesstime.precision configuration parameter.
        dfs.setTimes(path, -1, -1);

        Configuration conf = new Configuration();
        dfs.setConf(conf);

        dfs.setWriteChecksum(false);
        dfs.setWorkingDirectory(path);


        dfs.setBalancerBandwidth(10240);

        Path dir1 = util.createTmpDir("testSetQuota");
        dfs.setQuota(dir1, 10, 10);

        boolean f = dfs.setSafeMode(HdfsConstants.SafeModeAction.SAFEMODE_LEAVE);
        boolean f1 = dfs.setSafeMode(HdfsConstants.SafeModeAction.SAFEMODE_LEAVE, true);
        logger.info("setSafeMode:      " + f);
        logger.info("setSafeMode(ischecked):      " + f1);
        dfs.setWriteChecksum(true);

    }

    public void testModifyAclEntries() throws IOException {
        Path path = util.createTmpDir("testModifyAclEntries");
        dfs.modifyAclEntries(path, aclList);
        dfs.removeAcl(path);
    }

    //test API: create(path)
    public void testCreateWith() throws IOException {
        Path path = util.createTmpFile("testCreateWith");
        FSDataOutputStream outputStream = dfs.create(path);
        outputStream.write("hello".getBytes());
        outputStream.close();
        //assert true == dfs.isFile(path);
    }


    //test API: create(path, overwrite)
    public void testCreateWith1() throws IOException {
        Path path = util.createTmpFile("testCreateWith1");
        FSDataOutputStream outputStream = dfs.create(path, false);
        outputStream.write("hello".getBytes());
        outputStream.close();
    }

    //test API: create(path,replication)

    //test API: create(path,progress)
    public void testCreateWith2() throws IOException {
        Path path = util.createTmpFile("testCreateWith2");
        FSDataOutputStream outputStream = dfs.create(path, progressable);
        outputStream.write("hello".getBytes());
        outputStream.close();
    }

    public void testCreateWith3() throws IOException {
        short replication = 3;
        Path path = util.createTmpFile("testCreateWith3");
        FSDataOutputStream outputStream = dfs.create(path, replication);
        outputStream.write("hello".getBytes());
        outputStream.close();
    }

    //test API: create(path,replication,progress)
    public void testCreateWith4() throws IOException {
        Path path = util.createTmpFile("testCreateWith4");
        Short replication = 3;
        FSDataOutputStream outputStream = dfs.create(path, replication, progressable);
        outputStream.write("hello".getBytes());
        outputStream.close();
    }

    //test API: create(path,overwrite,buffsize)
    public void testCreateWith5() throws IOException {
        Path path = util.createTmpFile("testCreateWith5");
        int buffsize = 1024;
        FSDataOutputStream outputStream = dfs.create(path, false, buffsize);
        outputStream.write("hello".getBytes());
        outputStream.close();
    }


    //test API: create(path,overwrite,buffsize,prograss)
    public void testCreateWith6() throws IOException {
        Path path = util.createTmpFile("testCreateWith6");
        FSDataOutputStream outputStream = dfs.create(path, false, bufferSize, progressable);
        outputStream.write("hello".getBytes());
        outputStream.close();
    }

    //test API: create(path,overwrite,bufferSize,replication,blockSize)
    public void testCreateWith7() throws IOException {
        Path path = util.createTmpFile("testCreateWith7");
        Short replication = 3;
        FSDataOutputStream outputStream = dfs.create(path, false, bufferSize, replication, blockSize);
        outputStream.write("hello".getBytes());
        outputStream.close();
    }


    //test API: create(path,fsPermission,overwrite,bufferSize,replication,blockSize,progressable)
    public void testCreateWith8() throws IOException {
        Path path = util.createTmpFile("testCreateWith8");

        FSDataOutputStream outputStream = dfs.create(path, fsPermission, false, bufferSize, (short) 3, blockSize, progressable);
        outputStream.write("hello".getBytes());
        outputStream.close();
    }

    //test API: create(path,fsPermission,flags,bufferSize,replication,blockSize,progressable)
    public void testCreateWith9() throws IOException {
        Path path = util.createTmpFile("testCreateWith9");
        FSDataOutputStream outputStream = dfs.create(path, fsPermission, flags, bufferSize, (short) 3,
                blockSize, progressable);
        outputStream.write("hello".getBytes());
        outputStream.close();
    }

    //test API: create(path,fsPermission,flags,bufferSize,replication,blockSize,progressable,checksumOpt)
    public void testCreateWith10() throws IOException {
        Path path = util.createTmpFile("testCreateWith10");
        Options.ChecksumOpt checksumOpt = new Options.ChecksumOpt();
        FSDataOutputStream outputStream = dfs.create(path, fsPermission, flags, bufferSize, (short) 3,
                blockSize, progressable, checksumOpt);
        outputStream.write("hello".getBytes());
        outputStream.close();
    }

    //test API : mkdir(path)
    public void testMkdir() throws IOException {
        Path path = new Path(rootPath, "test-mkdir");
        dfs.mkdirs(path);
        //assert dfs.isDirectory(path) == true : "testMkdir-fail";
    }

    //test API : mkdir(path,permission)
    public void testMkdir1() throws IOException {
        Path path = new Path(rootPath, "test-mkdir1");
        dfs.mkdirs(path, fsPermission);
        //assert dfs.isDirectory(path) == true : "testMkdir-fail";
    }


    //test API:rename(Path src,dst)
    public void testRename() throws IOException {
        Path src = util.createTmpFile("test_for_rename");
        Path dst = new Path(rootPath, "test_for_rename_dist");
        dfs.rename(src, dst);

    }

    //test API : renameSnapshot()
    public void testSnapshotOper() throws IOException {

        String snapshotOldName = "oldSnapshot_for_test";
        Path snapShot = util.createTmpDir(snapshotOldName);
        dfs.disallowSnapshot(rootPath);
        dfs.allowSnapshot(snapShot);
        util.createTmpFile(snapshotOldName);
        dfs.createSnapshot(new Path(rootPath, snapShot), snapshotOldName);
        String snapshotNewName = "newSnapshot_for_test";
        dfs.renameSnapshot(snapShot, snapshotOldName, snapshotNewName);

        Path OldFile = new Path(rootPath + snapshotOldName);
        Path NewFile = new Path(rootPath + snapshotNewName);

        dfs.deleteSnapshot(snapShot, snapshotNewName);
        //assert dfs.isFile(OldFile) == false : "testRenameSnapshot-fail";
        //assert dfs.isFile(NewFile) == true : "testRenameSnapshot-fail";
    }


    public void testRemoveAcl() throws IOException {

        Path path = util.createTmpDir("testRemoveAcl");
        dfs.removeAcl(path);
        //this assert need 2.8 version
//        dfs.getAclStatus(path).getEntries()
//        assert dfs.getAclStatus(rootPath).getPermission().getAclBit() == false;
    }

    //test API: remove(path,recursive)
    public void testDelete() throws IOException {
        Path path = util.createTmpFile("for_test_delete");
        Path path1 = util.createTmpFile("for_test_delete1");
        logger.info("testDelete,recursive_false" + dfs.delete(path, false));
        //在for_test_delete1路径下建立文件和文件夹。文件夹下有文件
        logger.info("testDelete,recursive_true" + dfs.delete(path1, true));
    }

    //test API: deleteOnExit(path)
    public void testDeleteOnExit() throws IOException {
        // delete on close if path does exist
        Path path = new Path("/no_where");
        logger.info("testDeleteOnExit" + dfs.deleteOnExit(path));
        logger.info("testDeleteOnExit" + dfs.deleteOnExit(path));
    }


    //test API: open
    public void testOpen() throws IOException {
        Path path = util.createTmpFile("for-test-open");
        FSDataInputStream in = null;
        FSDataInputStream in1 = null;
        try {
            in = dfs.open(path);
            in1 = dfs.open(path, 5);
            logger.info("testOpen" + in.readLine());
            logger.info("testOpen" + in1.readLine());
        } finally {
            in.close();
            in1.close();
        }
    }

    //test API: isDirectory(Path) ,isFile(Path)
    public void testIsFile() throws IOException {
        Path filePath = util.createTmpFile("testIsFile");
        Path filedir = util.createTmpDir("testIsFile1");
        logger.info("testIsFile:    " + dfs.isFile(filePath));
        logger.info("testIsFile:    " + dfs.isFile(filedir));
    }

    //test API : listFile(path.recursive)
    public void testListFile() throws IOException {
        String str = "testListFile10";
        Path path = util.createTmpFile(str);

        RemoteIterator<LocatedFileStatus> list1 = dfs.listFiles(path, false);
        int count = 0;
        int count1 = 0;
        while (list1.hasNext()) {
            count++;
            list1.next();
        }
        RemoteIterator<LocatedFileStatus> list2 = dfs.listFiles(path, true);
        while (list2.hasNext()) {
            count1++;
            list2.next();
        }

        Path path1 = util.createLocalTmpDir(str);

        RemoteIterator<LocatedFileStatus> list3 = dfs.listFiles(path1, false);
        int count2 = 0;
        while (list3.hasNext()) {
            count2++;
            list3.next();
        }

        RemoteIterator<LocatedFileStatus> list4 = dfs.listFiles(path1, true);
        int count3 = 0;
        while (list4.hasNext()) {
            count3++;
            list4.next();
        }
        logger.info("testListFile(),count num:    non-inrecursive:" + count + ",inrecursive" + count1);
        logger.info("  listFiles(Dir):" + count2 + "listFiles(true,Dir):" + count3);
    }


    //test API : listCorruptFileBlocks()
    public void testListCorruptFileBlocks() throws IOException {
        Path path = util.createTmpFile("testListCorruptFileBlocks");
        BlockLocation[] blockLocations = dfs.getFileBlockLocations(path, 0, 1);
        //one corrupt
        //应该是true的，先用false测试一下，不行的话应该是有问题
        blockLocations[0].setCorrupt(false);
        RemoteIterator<Path> list1 = dfs.listCorruptFileBlocks(path);
        int count = 0;
        while (list1.hasNext()) {
            count++;
            list1.next();
        }
        //结果应该为1
        logger.info("testListCorruptFileBlocks(),count num: " + count);
    }

    public void testListCorruptFileBlocks_fot_test() throws IOException {
        Path path = util.createTmpFile("testListCorruptFileBlocks1");
        BlockLocation[] blockLocations = dfs.getFileBlockLocations(path, 0, 10);
        //one corrupt
        blockLocations[0].setCorrupt(true);
        //应该是true的，先用false测试一下，不行的话应该是有问题
        RemoteIterator<Path> list1 = dfs.listCorruptFileBlocks(path);
        int count = 0;
        while (list1.hasNext()) {
            count++;
            list1.next();
        }
        //结果应该为1
        logger.info("testListCorruptFileBlocks(),count num: " + count);
    }


    public void testListLocatedStatus() throws IOException {
        Path path = util.createTmpFile("testListLocatedStatus");
//        Path dirPath = util.createTmpDir("testListLocatedStatus_dir");
//        util.createTmpDir("testListLocatedStatus_dir");
        RemoteIterator<LocatedFileStatus> f = dfs.listLocatedStatus(path);
        RemoteIterator<LocatedFileStatus> dirF = dfs.listLocatedStatus(rootPath);
        LocatedFileStatus lo = f.next();
        LocatedFileStatus dirLo = dirF.next();
        logger.info("testListLocatedStatus(),count num: " + lo.toString() + lo.getBlockLocations()[0].toString());
        logger.info("testListLocatedStatus(),count num: " + dirLo.toString());
    }

    public void testListStatus() throws IOException {
        Path path = util.createTmpFile("testListStatus");
        FileStatus[] f = dfs.listStatus(path);
        logger.info("testListStatus(),count num: " + f[0].toString());
    }


    public void testListStatus1() throws IOException {
        Path path = util.createTmpFile("testListStatus1");
        FileStatus[] f = dfs.listStatus(path, pathFilter);
        logger.info("testListStatus1(),count num: " + f.length);
    }

    public void testListStatus2() throws IOException {
        Path path1 = util.createTmpFile("testListStatus2_1");
        Path path2 = util.createTmpFile("testListStatus2_2");
        Path[] paths = new Path[]{path1, path2};
        FileStatus[] f = dfs.listStatus(paths);
        logger.info("testListStatus2(),count1 num: " + f[0].toString() + "count2 num:" + f[1].toString());
    }

    public void testListStatus3() throws IOException {
        Path path1 = util.createTmpFile("testListStatus3_1");
        Path path2 = util.createTmpFile("testListStatus3_2");
        Path[] paths = new Path[]{path1, path2};
        PathFilter pathFilter = new PathFilter() {
            @Override
            public boolean accept(Path path) {
                return false;
            }
        };
        FileStatus[] f = dfs.listStatus(paths, pathFilter);
        logger.info("testListStatus3(),count num: " + f.length);
    }

    //test API: append(path)
    public void testAppend() throws IOException {
        String str = "testAppend";
        Path path = util.createTmpFile(str);
        FSDataOutputStream outputStream = dfs.append(path);
        outputStream.write(str.getBytes());
        outputStream.close();
        String fileContext = util.readFile(path);
        //assert 0 == fileContext.compareTo("for_testtestApppend");
    }

    //test API: append(path.bufferSize)
    public void testAppend1() throws IOException {
        String str = "testAppend1";
        Path path = util.createTmpFile(str);
        //bufferSize : char length?
        FSDataOutputStream outputStream = dfs.append(path, 1);
        outputStream.write(str.getBytes());
        outputStream.close();
        String fileContext = util.readFile(path);
        //assert 0 == fileContext.compareTo("for_testtest");
    }

    //test API: append(path.bufferSize,progressable)
    public void testAppend2() throws IOException {
        String str = "testAppend2";
        Path path = util.createTmpFile(str);
        FSDataOutputStream outputStream = dfs.append(path, 0, progressable);
        outputStream.write(str.getBytes());
        outputStream.close();
        String fileContext = util.readFile(path);
    }


//    public void testCopyFromLocalFile() throws IOException {
//        String f = "local_file1";
//        //Path localFile = util.createLocalTmpFile(f);
//        dfs.copyFromLocalFile(uploadPath, uploadPath);
//    }

//    //delete the local file after copy the local file to hdfs
//    public void test
// FromLocalFile() throws IOException {
//        String f1 = "local_file2";
//        String f2 = "local_file3";
////        Path localFile1 = util.createLocalTmpFile(f1);
////        Path localFile2 = util.createLocalTmpFile(f2);
//
//        Path[] paths = new Path[]{uploadPath, uploadPath1};
//        Path dst = new Path(rootPath, "testMoveFromLocalFile");
//        dfs.mkdirs(dst);
//        dfs.moveFromLocalFile(paths, dst);
//    }

//    //test API: copyFromLocalFile(delSrc,overwrite,path)
//    public void testCopyFromLocalFile1() throws IOException {
//        String str = "local_file4";
//        Path dst = new Path(rootPath, "testCopyFromLocalFile1");
//        dfs.copyFromLocalFile(false, uploadPath, uploadPath);
//    }

//    //test API: copyFromLocalFile(delSrc,overwrite,path,path)
//    public void testCopyFromLocalFile2() throws IOException {
//        String str = "local_file5";
//        dfs.copyFromLocalFile(false, true, uploadPath, uploadPath);
//    }

//    public void testCopyToLocalFile() throws IOException {
////        Path path = util.createTmpFile("testCopyToLocalFile");
//        dfs.copyToLocalFile(downloadPath, downloadPath);
//
//        //assert true == file.isFile();
//    }

    //
//    public void testCopyToLocalFile1() throws IOException {
//        Path path = util.createTmpFile("testCopyToLocalFile1");
//        dfs.copyToLocalFile(false, downloadPath, downloadPath);
//        File file = new File(path.toString());
//        //assert true == file.isFile();
//    }

//    public void testCopyToLocalFile2() throws IOException {
//        Path path = util.createTmpFile("testCopyToLocalFile2");
//        dfs.copyToLocalFile(false, downloadPath, downloadPath, false);
//        File file = new File(path.toString());
//        //assert true == file.isFile();
//    }


//    public void testDefaultAcl() {
//        List<AclEntry>  list=new ArrayList<>();
//        AclEntry aclEntry=new AclEntry(USER,"",READ,ACCESS);
//       // AclEntry aclEntry=new AclEntry(USER,"",READ,new AclEntryScope());
//        list.add(aclEntry);
//        dfs.setAcl(rootPath,);
//        dfs.setOwner();
//    }


    //test API : testEmptyDelegationToken
    public void testEmptyDelegationToken() throws IOException {
//        Token<DelegationTokenIdentifier> tokenId = dfs.getDelegationToken("dfs");
//        logger.info("token.length" + tokenId.getIdentifier().toString());
//        logger.info("token.length" + tokenId.getIdentifier().toString());
//        logger.info("token.length" + tokenId.getKind().toString());
//        logger.info("token.length" + tokenId.getPassword().toString());

            dfs.getDelegationToken("");

    }


//    public void testFileSystemCloseAll() throws Exception {
//        URI address = dfs.getDefaultUri(conf);
//        FileSystem.closeAll();
//        conf = dfs.getConf();
//        dfs.setDefaultUri(conf, address);
//        dfs.get(conf);
//        dfs.get(conf);
//        dfs.closeAll();
//    }
}
