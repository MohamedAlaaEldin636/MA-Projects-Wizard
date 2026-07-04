---
layout: .components.layouts.MarkdownLayout
title: About
---

# MA Projects Wizard

This project is designed to simplify and accelerate common tasks in Kotlin Multiplatform (KMP) development, Inshallah.

This App Represents version ${SiteGlobals.AppVersion} Inshallah.

---

## 🎯 The Purpose

While developing with KMP (Kotlin Multiplatform), I noticed a persistent workflow challenge: visualizing vector drawables. Currently, the IDE can only preview them within the Android target, not inside the `commonMain` source set. This forces developers to manually copy the asset into the Android module just to view it, and then delete it afterward—a tedious hassle.

Furthermore, importing an SVG as a VectorDrawable (or vice versa) using the native IDE tools automatically dumps the asset into the Android target instead of `commonMain`.

To solve this, I decided to build a dedicated desktop and web application to handle these asset pipelines seamlessly. I hope it becomes a valuable tool for the community, Inshallah.

## 🛠️ Development Notes

* **Why an App Instead of a Plugin?**
  Since building an IDE plugin introduces significant development overhead, I started with a Desktop/Web application for faster iteration. If this tool proves highly effective, I plan to migrate it into an official IDE plugin to bring these features directly into the workspace, Inshallah.

* **Future Tools:**
  Currently, only the asset conversion tool is live. However, I have built several other internal utilities. If they prove helpful, I will integrate them here in the future, Inshallah. This is why the project carries a generic name rather than one specific to image conversion.

* **To-Do Roadmap:**
  I have included a dedicated [To-Do page](/todo) outlining features I plan to build to make this tool even more helpful in the future, Inshallah. Please feel free to look it over and suggest any additional features or ideas you would like to see implemented Inshallah.

### 📜 Change Log

#### ➤ 1.0.0

- Created a new KMP project with Desktop & Web Targets El7mdullah & Inshallah.
  - Contains 3 Modules
    - shared => shares logic only between other modules (No UI at all in this module) Inshallah.
    - desktopApp => contains UI for desktop target via compose multiplatform Inshallah.
    - webApp => contains UI for web target via kobweb Inshallah.
- Added 4 destinations
  - Home
    - Simple Landing Page of the App to redirect to other pages Inshallah.
  - Image Renderer And Converter
    - Visualizes and converts vector drawables to/from SVG via a picked file or via pasted 
    text Inshallah.
  - Todo
    - Contains todos that I think should be implemented later to improve this Project Inshallah.
  - About
    - Contains info about the project Inshallah.

### 🤝 Attributions

* Some boilerplate files and configurations originate from the initial template provided by the `kobweb create` command, Inshallah.
