
```
# 查看服务器外网IP
curl ifconfig.me

# 搜索历史命令
history | grep 搜索关键字

# 执行历史命令
!历史命令ID

# 添加防火墙: 开放端口/关闭端口/禁止某个IP访问
vi /etc/sysconfig/iptables
# 重启防火墙
service iptables restart
# 保存规则, 保证服务器重启后仍旧有效
service iptables save
# 查看开放的端口
iptables -L -n
```

