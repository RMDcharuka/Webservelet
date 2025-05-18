
## How to run this locally ?

### Prerequisites

- Install [Docker](https://docs.docker.com/get-docker/)
- Install [Docker Compose](https://docs.docker.com/compose/install/)

### Steps

- Download the `.env` file from [envshare.dev](https://envshare.dev/unseal#rCMEWRX6cjA6Yu7JKyCjK5dEa6vkQFMJhgqMvx9k6wc9) and place it in the `src/main/resources` directory of the project. You can also use the following command to download it directly:

```bash
curl -o src/main/resources/.env https://envshare.dev/unseal#rCMEWRX6cjA6Yu7JKyCjK5dEa6vkQFMJhgqMvx9k6wc9
```

- Run with docker compose
```bash
docker compose up
```

- Open your browser and go to [http://localhost:8080](http://localhost:8080)

- You can stop the server by pressing `CTRL + C` in the terminal where you ran the command.

## View the deployed application

You can view the deployed application at [https://webservelet.fly.dev](https://webservelet.fly.dev)
