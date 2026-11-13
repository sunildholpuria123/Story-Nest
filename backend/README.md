# StoryNest Backend (Cloudflare Worker)

This Worker keeps the Gemini API key off the Android device and calls Google's recommended Gemini Interactions API.

## Requirements

- Node.js LTS
- A Cloudflare account
- A Gemini API key created in Google AI Studio

## Local setup

```bash
npm install
cp .dev.vars.example .dev.vars
```

Edit `.dev.vars` and add your **newly rotated** Gemini API key. Never commit `.dev.vars`.

```bash
npm run dev
```

Health check:

```bash
curl http://localhost:8787/api/v1/health
```

Generate a story:

```bash
curl -X POST http://localhost:8787/api/v1/stories/generate \
  -H "content-type: application/json" \
  -d '{"prompt":"Write a short, kind story for a 6-year-old about a rabbit helping a friend."}'
```

## Deploy

```bash
npx wrangler login
npx wrangler secret put GEMINI_API_KEY
npm run deploy
```

Copy the deployed `https://<worker>.<subdomain>.workers.dev/` URL.

The `STORY_LIMITER` binding allows approximately 10 generation requests per 60 seconds per client IP in each Cloudflare location. Cloudflare documents this as an approximate regional limit, not a strict global quota. The key is IP-based only for this MVP. Once Supabase Auth is added, change it to the verified user ID.

## Android configuration

In the Android project root, create or update `local.properties`:

```properties
sdk.dir=YOUR_LOCAL_ANDROID_SDK_PATH
STORYNEST_API_BASE_URL=https://YOUR-WORKER.YOUR-SUBDOMAIN.workers.dev/
```

The URL must use HTTPS and end with `/`. There is no Gemini key in the Android app.

## API contract

`POST /api/v1/stories/generate`

Request:

```json
{ "prompt": "..." }
```

Response: Gemini Interactions API output adapted to the existing Android `candidates` response model, so the existing `GeminiStoryParser` and Room save flow remain in place.

## Current scope and next phase

This MVP securely proxies Gemini and adds basic request validation and approximate rate limiting. It does not yet implement Supabase Auth, cloud story sync, per-user daily quotas, or parent accounts. Do not expose this endpoint as a public production service until user authentication and server-side per-user quotas are implemented.

## Supabase cloud library (Phase 2)

A starter RLS-protected schema is in `supabase/schema.sql`. It is intentionally
not wired into the Android app yet: the current app has no account/login flow.
Do not use a Supabase service-role key in Android. Implement Supabase Auth first,
then send the user's access token to a server-side endpoint and enforce ownership
with RLS before enabling cloud story sync.
