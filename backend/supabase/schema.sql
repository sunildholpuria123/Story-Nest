-- StoryNest cloud story library.
-- Run in Supabase SQL Editor. RLS ensures each user can access only their own stories.

create table if not exists public.user_stories (
    id uuid primary key,
    user_id uuid not null references auth.users(id) on delete cascade,
    title text not null,
    content text not null,
    character_name text not null default '',
    category text not null default '',
    mood text not null default '',
    topic text,
    language text not null default 'hi',
    is_favorite boolean not null default false,
    created_at timestamptz not null default now(),
    last_read_sentence integer not null default -1,
    updated_at timestamptz not null default now(),
    is_deleted boolean not null default false
);

create index if not exists user_stories_user_created_at_idx
    on public.user_stories (user_id, created_at desc);

alter table public.user_stories enable row level security;

drop policy if exists "Users read own stories" on public.user_stories;
create policy "Users read own stories"
    on public.user_stories for select
    using (auth.uid() = user_id);

drop policy if exists "Users create own stories" on public.user_stories;
create policy "Users create own stories"
    on public.user_stories for insert
    with check (auth.uid() = user_id);

drop policy if exists "Users update own stories" on public.user_stories;
create policy "Users update own stories"
    on public.user_stories for update
    using (auth.uid() = user_id)
    with check (auth.uid() = user_id);

drop policy if exists "Users delete own stories" on public.user_stories;
create policy "Users delete own stories"
    on public.user_stories for delete
    using (auth.uid() = user_id);
