#!/usr/bin/env python3
"""
Remove comments from project files. Creates a backup copy with extension .orig before modifying.
Use with caution — this will remove inline and block comments in many file types and may alter strings that contain comment-like sequences.
"""
import io
import os
import re

ROOT = os.path.join(os.path.dirname(__file__), '..').replace('\\', '/')
EXTENSIONS = {
    # extension: handlers (order matters)
    '.java': ['multiline_c', 'line_cpp'],
    '.js': ['multiline_c', 'line_cpp'],
    '.jsx': ['multiline_c', 'line_cpp'],
    '.ts': ['multiline_c', 'line_cpp'],
    '.css': ['multiline_c'],
    '.html': ['html', 'multiline_c', 'line_cpp'],
    '.htm': ['html', 'multiline_c', 'line_cpp'],
    '.jsp': ['jsp', 'html', 'multiline_c', 'line_cpp'],
    '.jspx': ['jsp', 'html', 'multiline_c', 'line_cpp'],
    '.jspf': ['jsp', 'html', 'multiline_c', 'line_cpp'],
    '.properties': ['props'],
    '.xml': ['html'],
    '.sql': ['line_sql', 'multiline_c'],
    '.txt': ['line_hash'],
    '.md': ['html'],
}

# regex handlers
RE = {
    'multiline_c': re.compile(r'/\*[\s\S]*?\*/'),
    'line_cpp': re.compile(r'(?m)//[^"\n]*$'),
    'html': re.compile(r'<!--([\s\S]*?)-->'),
    'jsp': re.compile(r'<%--([\s\S]*?)--%>'),
    'props': re.compile(r'(?m)^\s*[#!].*$\n?'),
    'line_hash': re.compile(r'(?m)^\s*#.*$\n?'),
    'line_sql': re.compile(r'(?m)--[^"\n]*$'),
}

SKIP_DIRS = {'.git', 'target', 'node_modules', '__pycache__'}

modified = []
errors = []

for dirpath, dirnames, filenames in os.walk(ROOT):
    # skip directories
    parts = set(dirpath.replace('\\', '/').split('/'))
    if parts & SKIP_DIRS:
        continue

    for fname in filenames:
        path = os.path.join(dirpath, fname)
        _, ext = os.path.splitext(fname)
        ext = ext.lower()
        if ext not in EXTENSIONS:
            continue

        try:
            with io.open(path, 'r', encoding='utf-8') as f:
                content = f.read()
        except Exception:
            try:
                with io.open(path, 'r', encoding='latin-1') as f:
                    content = f.read()
            except Exception as e:
                errors.append((path, str(e)))
                continue

        original = content
        handlers = EXTENSIONS[ext]
        # apply jsp and html first to remove server-side and HTML comments
        for h in handlers:
            rx = RE.get(h)
            if not rx:
                continue
            content = rx.sub('', content)

        # additionally remove trailing whitespace-only lines produced
        content = re.sub(r'(?m)^[ \t]+$\n', '', content)

        if content != original:
            backup = path + '.orig'
            try:
                if not os.path.exists(backup):
                    with io.open(backup, 'w', encoding='utf-8') as b:
                        b.write(original)
                with io.open(path, 'w', encoding='utf-8') as f:
                    f.write(content)
                modified.append(path)
            except Exception as e:
                errors.append((path, str(e)))

# print summary
print('Remove-comments script finished.')
print('Files modified:', len(modified))
for m in modified[:200]:
    print('M', m)
if errors:
    print('\nErrors:')
    for p, e in errors:
        print('E', p, e)
else:
    print('No read/write errors.')
