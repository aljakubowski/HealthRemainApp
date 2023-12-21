services:

    admin-panel     localhost:8080
    physician       localhost:8083
    visit           localhost:8084
    patient         localhost:8085

    mongo-express   localhost:8081
    eureka          localhost:8761


run mongo
            docker compose up -d
            // ? -> docker compose -f docker-compose.yml -d
            docker compose down



