# MA Projects Wizard

This project is designed to simplify and accelerate common tasks in Kotlin Multiplatform (KMP) development, Inshallah.

You can visit the website of this project hosted on GitHub Pages 
[here 🡵 👉🌐](https://mohamedalaaeldin636.github.io/MA-Projects-Wizard/) Inshallah.

Note the project is expected to add desktop support soon Inshallah.

---

## The Purpose

While developing with KMP (Kotlin Multiplatform), I noticed a persistent workflow challenge: visualizing vector drawables. Currently, the IDE can only preview them within the Android target, not inside the `commonMain` source set. This forces developers to manually copy the asset into the Android module just to view it, and then delete it afterward—a tedious hassle.

Furthermore, importing an SVG as a VectorDrawable (or vice versa) using the native IDE tools automatically dumps the asset into the Android target instead of `commonMain`.

To solve this, I decided to build a dedicated desktop and web application to handle these asset pipelines seamlessly. I hope it becomes a valuable tool for the community, Inshallah.

## Development Notes

* **Why an App Instead of a Plugin?**
  Since building an IDE plugin introduces significant development overhead, I started with a Desktop/Web application for faster iteration. If this tool proves highly effective, I plan to migrate it into an official IDE plugin to bring these features directly into the workspace, Inshallah.

* **Future Tools:**
  Currently, only the asset conversion tool is live. However, I have built several other internal utilities. If they prove helpful, I will integrate them here in the future, Inshallah. This is why the project carries a generic name rather than one specific to image conversion.

* **To-Do Roadmap:**
  I have included a dedicated [To-Do page](/webApp/src/jsMain/resources/markdown/Todo.md) outlining features I plan to build to make this tool even more helpful in the future, Inshallah. Please feel free to look it over and suggest any additional features or ideas you would like to see implemented Inshallah.

### Attributions

* Some boilerplate files and configurations originate from the initial template provided by the `kobweb create` command, Inshallah.

### [License](LICENSE)

```
Copyright © 2026 Mohamed Alaa

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and limitations under the License.
```

| Permissions         | Limitations           | Conditions   |
| ------------------- | --------------------- | ----------- |
| :heavy_check_mark: Commercial Use | :x: Trademark use | :information_source: License and copyright notice |
| :heavy_check_mark: Modification | :x: Liability | :information_source: State changes |
| :heavy_check_mark: Distribution | :x: Warranty | - |
| :heavy_check_mark: Patent use | - | - |
| :heavy_check_mark: Private use | - | - |
