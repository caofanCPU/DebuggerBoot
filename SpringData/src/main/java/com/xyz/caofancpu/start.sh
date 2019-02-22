SERVER_PORT=8988
HOME=`pwd`
APP_EXEC_JAR="bin/report-service-0.0.1-SNAPSHOT.jar"
PIDFILE="$HOME/pid"
IP=`ifconfig -a|grep inet|grep -v 127.0.0.1|grep -v inet6|awk '{print $2}'`
checkRunning(){
    if [ -f "$PIDFILE" ]; then
       if  [ -z "`cat $PIDFILE`" ];then
        echo "ERROR: Pidfile '$PIDFILE' exists but contains no pid"
        return 2
       fi
       PID="`cat $PIDFILE`"
       RET="`ps -p $PID|grep java`"
       if [ -n "$RET" ];then
         return 0;
       else
         return 1;
       fi
    else
         return 1;
    fi
}

if ( checkRunning );then
    PID=`cat $PIDFILE`
    echo "INFO: Process with pid '$PID' is already running"
    exit 0
fi

java -server -Xmx1g \
     -DLog4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector \
     -jar $APP_EXEC_JAR \
     --server.port=$SERVER_PORT \
     --metric.home=$HOME \
     --metric.ip=$IP \
     --eureka.instance.ip-address=$IP \
     --spring.profiles.active=t \
     --spring.config.location=$HOME/conf/application-alpha.yml \
     --logging.config=$HOME/conf/logback.xml > /dev/null 2>&1 &
echo $! > "$HOME/pid";
PID="`cat $PIDFILE`"

echo "INFO: Starting - Process $PID, server.port: $SERVER_PORT"


