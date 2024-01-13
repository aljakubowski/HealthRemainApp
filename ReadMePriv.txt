services:

    apiGateway      localhost:8082

    physician       localhost:8083
    patient         localhost:8086
    visit           localhost:8084

    mongo-express   localhost:8081
    eureka          localhost:8761


run mongo
            docker compose up -d
            // ? -> docker compose -f docker-compose.yml -d
            docker compose down



