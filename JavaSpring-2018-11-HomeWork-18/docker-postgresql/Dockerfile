FROM postgres:11.2

ENV POSTGRES_USER dbuser
ENV POSTGRES_PASSWORD password
ENV POSTGRES_DB db

ENV ENTRYPOINT docker-entrypoint-initdb.d

COPY sql/*.sql $ENTRYPOINT/

EXPOSE 5432
CMD [ "/docker-entrypoint.sh", "postgres" ]
