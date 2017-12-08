# git例子  xwwww

## git初始化
git init 
## 给远程地址起个别名
git remote add zjj git@github.com:jaychoucc/wechat.git
## 添加当前所有到本地仓库
git add .
## 提交 ’init’ 到本地仓库
git commit -m ‘init’
## 推送本地仓库到远程地址 master 分支
git push zjj master


# 拉取最新 master 分支
git pull zjj master

# git remote 相关操作
## 添加远程地址
git remote add zjj git@github.com:jaychoucc/wechat.git
## 删除远程地址
git remote rm {name}
## 查询远程地址别名
git remote

# 克隆远程项目
git clone git@github.com:jaychoucc/wechat.git

