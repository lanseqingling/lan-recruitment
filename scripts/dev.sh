#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
BACKEND_DIR="$ROOT_DIR/backend"
FRONTEND_DIR="$ROOT_DIR/frontend"

if ! command -v mvn >/dev/null 2>&1; then
  echo "缺少命令：mvn"
  exit 1
fi
if ! command -v npm >/dev/null 2>&1; then
  echo "缺少命令：npm"
  exit 1
fi

export VITE_API_BASE_URL="${VITE_API_BASE_URL:-http://localhost:8080}"

if [[ "${1:-}" == "--dry-run" ]]; then
  echo "ROOT_DIR=$ROOT_DIR"
  echo "VITE_API_BASE_URL=$VITE_API_BASE_URL"
  echo "(cd \"$BACKEND_DIR\" && mvn -DskipTests spring-boot:run)"
  echo "(cd \"$FRONTEND_DIR\" && npm run dev)"
  exit 0
fi

if [[ ! -d "$FRONTEND_DIR/node_modules" ]]; then
  (cd "$FRONTEND_DIR" && npm i)
fi

(cd "$BACKEND_DIR" && mvn -DskipTests spring-boot:run) &
BACKEND_PID=$!

(cd "$FRONTEND_DIR" && npm run dev) &
FRONTEND_PID=$!

cleanup() {
  kill "$BACKEND_PID" "$FRONTEND_PID" >/dev/null 2>&1 || true
}
trap cleanup EXIT INT TERM

echo "后端：http://localhost:8080"
echo "前端：http://localhost:5173"
echo "按 Ctrl+C 退出"

while true; do
  if ! kill -0 "$BACKEND_PID" >/dev/null 2>&1; then
    exit 1
  fi
  if ! kill -0 "$FRONTEND_PID" >/dev/null 2>&1; then
    exit 1
  fi
  sleep 1
done

