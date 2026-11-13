-- StoryNest cloud story library (Phase 2: enable only after Supabase Auth is integrated).
-- Run in Supabase SQL Editor. Row Level Security prevents users reading other users' stories.

create table if not exists public.stories (
    id uuid primary key default gen_random_uuid(),
    user_id uuid not null references auth.users(id) on delete cascade,
    title text not null,
    content text not null,
    category text not null default '',
    topic text,
    language text not null default 'hi-IN',
    created_at timestamptz not null default now()
);

create index if not exists stories_user_created_at_idx
    on public.stories (user_id, created_at desc);

alter table public.stories enable row level security;

drop policy if exists "Users read own stories" on public.stories;
create policy "Users read own stories"
    on public.stories for select
    using (auth.uid() = user_id);

drop policy if exists "Users create own stories" on public.stories;
create policy "Users create own stories"
    on public.stories for insert
    with check (auth.uid() = user_id);

drop policy if exists "Users update own stories" on public.stories;
create policy "Users update own stories"
    on public.stories for update
    using (auth.uid() = user_id)
    with check (auth.uid() = user_id);

drop policy if exists "Users delete own stories" on public.stories;
create policy "Users delete own stories"
    on public.stories for delete
    using (auth.uid() = user_id);
