# Currency fluctuations service

The task: [link](TODO.md)

### Environment variables:

| Name        | Description                |
|-------------|----------------------------|
| OER_TOKEN   | Open Exchange Rates App ID |
| GIPHY_TOKEN | Giphy Api Key              |

## Docker

```bash
./gradlew bootBuildImage --imageName=fluctuations
docker run --env-file ./env.list -p 9000:9000 -t fluctuations
```

```bash
# env.list
OER_TOKEN=...
GIPHY_TOKEN=...
```

## Usage

### `GET /v1/gifs/currency-fluctuations/{currency}`