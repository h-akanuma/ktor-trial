FROM mysql:8-debian

# copy conf file
COPY ./docker/ktor_trial_db/mysql.conf.d /etc/mysql/mysql.conf.d

# Set debian default locale to ja_JP.UTF-8
RUN apt-get update && \
    apt-get install -y locales && \
    rm -rf /var/lib/apt/lists/* && \
    echo "ja_JP.UTF-8 UTF-8" > /etc/locale.gen && \
    locale-gen ja_JP.UTF-8
ENV LC_ALL ja_JP.UTF-8

# Set MySQL character
RUN { \
    echo '[mysqld]'; \
    echo 'character-set-server=utf8mb4'; \
    echo 'collation-server=utf8mb4_general_ci'; \
    echo 'max_allowed_packet = 100M'; \
    echo 'innodb_buffer_pool_size = 100M'; \
    echo 'innodb_log_file_size=100M'; \
    echo '[client]'; \
    echo 'default-character-set=utf8mb4';\
} > /etc/mysql/conf.d/charset.cnf

